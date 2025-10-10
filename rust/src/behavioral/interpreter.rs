use std::collections::HashMap;

trait Expr {
    fn interpret(&self, ctx: &HashMap<String, bool>) -> bool;
}

struct Var(String);
impl Expr for Var {
    fn interpret(&self, ctx: &HashMap<String, bool>) -> bool {
        *ctx.get(&self.0).unwrap_or(&false)
    }
}

struct And(Box<dyn Expr>, Box<dyn Expr>);
impl Expr for And {
    fn interpret(&self, ctx: &HashMap<String, bool>) -> bool {
        self.0.interpret(ctx) && self.1.interpret(ctx)
    }
}

struct Or(Box<dyn Expr>, Box<dyn Expr>);
impl Expr for Or {
    fn interpret(&self, ctx: &HashMap<String, bool>) -> bool {
        self.0.interpret(ctx) || self.1.interpret(ctx)
    }
}

struct Not(Box<dyn Expr>);
impl Expr for Not {
    fn interpret(&self, ctx: &HashMap<String, bool>) -> bool {
        !self.0.interpret(ctx)
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        // (admin && active) || (vip && !banned)
        let rule: Box<dyn Expr> = Box::new(Or(
            Box::new(And(
                Box::new(Var("admin".into())),
                Box::new(Var("active".into())),
            )),
            Box::new(And(
                Box::new(Var("vip".into())),
                Box::new(Not(Box::new(Var("banned".into())))),
            )),
        ));

        let mut ctx1 = HashMap::new();
        ctx1.insert("admin".into(), true);
        ctx1.insert("active".into(), true);
        println!("{}", rule.interpret(&ctx1)); // true

        let mut ctx2 = HashMap::new();
        ctx2.insert("vip".into(), true);
        ctx2.insert("banned".into(), false);
        println!("{}", rule.interpret(&ctx2)); // true

        let mut ctx3 = HashMap::new();
        ctx3.insert("vip".into(), true);
        ctx3.insert("banned".into(), true);
        println!("{}", rule.interpret(&ctx3)); // false
    }
}
