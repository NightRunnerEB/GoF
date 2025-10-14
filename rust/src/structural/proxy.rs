use std::{collections::HashMap, thread, time::Duration};

trait Storage {
    fn get(&self, key: &str) -> Option<String>;
}

// Real storage (slow)
#[derive(Clone, Copy)]
struct RealStorage;
impl Storage for RealStorage {
    fn get(&self, key: &str) -> Option<String> {
        println!("IO: fetching '{key}' from slow storage...");
        thread::sleep(Duration::from_millis(200)); // simulate I/O
        Some(format!("value_of_{key}"))
    }
}

// User role
#[derive(Clone, Copy)]
enum Role {
    Admin,
    User,
}

// Proxy: adds authorization + simple cache
struct StorageProxy<S: Storage> {
    inner: S,
    role: Role,
    cache: std::cell::RefCell<HashMap<String, String>>,
}

impl<S: Storage> StorageProxy<S> {
    fn new(inner: S, role: Role) -> Self {
        Self {
            inner,
            role,
            cache: Default::default(),
        }
    }
}

impl<S: Storage> Storage for StorageProxy<S> {
    fn get(&self, key: &str) -> Option<String> {
        // Protection: block private keys for non-admins
        if matches!(self.role, Role::User) && key.starts_with("secret:") {
            println!("DENY: '{}' for User", key);
            return None;
        }
        // Cache lookup
        if let Some(value) = self.cache.borrow().get(key).cloned() {
            println!("CACHE: hit '{key}'");
            return Some(value);
        }
        let value = self.inner.get(key)?;
        self.cache
            .borrow_mut()
            .insert(key.to_string(), value.clone());
        Some(value)
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        let real = RealStorage;

        // Regular user without privileges
        let user_store = StorageProxy::new(real, Role::User);
        println!("{:?}", user_store.get("config:theme")); // slow, then cached
        println!("{:?}", user_store.get("config:theme")); // cache hit
        println!("{:?}", user_store.get("secret:token")); // denied

        // Admin with full rights
        let admin_store = StorageProxy::new(real, Role::Admin);
        println!("{:?}", admin_store.get("secret:token")); // allowed
    }
}
