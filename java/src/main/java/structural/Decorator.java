package structural;

interface DataSource {

    void writeData(String data);

    String readData();
}

class FileDataSource implements DataSource {

    private final String filename;

    public FileDataSource(String filename) {
        this.filename = filename;
    }

    @Override
    public void writeData(String data) {
        System.out.println("File " + filename + ": " + data);
    }

    @Override
    public String readData() {
        return "Reading from file " + filename;
    }
}

// Base decorator
abstract class DataSourceDecorator implements DataSource {

    protected DataSource wrappee;

    public DataSourceDecorator(DataSource wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public void writeData(String data) {
        wrappee.writeData(data);
    }

    @Override
    public String readData() {
        return wrappee.readData();
    }
}

class EncryptionDecorator extends DataSourceDecorator {

    public EncryptionDecorator(DataSource wrappee) {
        super(wrappee);
    }

    @Override
    public void writeData(String data) {
        super.writeData("[encrypted]" + data);
    }

    @Override
    public String readData() {
        return "[decrypted]" + super.readData();
    }
}

class CompressionDecorator extends DataSourceDecorator {

    public CompressionDecorator(DataSource wrappee) {
        super(wrappee);
    }

    @Override
    public void writeData(String data) {
        super.writeData("[compressed]" + data);
    }

    @Override
    public String readData() {
        return "[uncompressed]" + super.readData();
    }
}

// Builder helper that chains decorators together
class DataSourceBuilder {

    private DataSource current;

    public DataSourceBuilder(DataSource base) {
        this.current = base;
    }

    public DataSourceBuilder addEncryption() {
        current = new EncryptionDecorator(current);
        return this;
    }

    public DataSourceBuilder addCompression() {
        current = new CompressionDecorator(current);
        return this;
    }

    public DataSource build() {
        return current;
    }
}

@SuppressWarnings("unused")
final class DecoratorExample {

    private DecoratorExample() {
    }

    public static void example() {
        DataSource dataSource = new DataSourceBuilder(new FileDataSource("data.txt"))
                .addCompression()
                .addEncryption()
                .build();

        dataSource.writeData("Hello World");
        System.out.println(dataSource.readData());
    }
}
