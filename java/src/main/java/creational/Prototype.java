package creational;

interface Prototype<T> {

    T copy();
}

class ReportTemplate implements Prototype<ReportTemplate> {

    String title;
    String footer;
    String watermark;

    ReportTemplate(String title, String footer, String watermark) {
        this.title = title;
        this.footer = footer;
        this.watermark = watermark;
    }

    @Override
    public ReportTemplate copy() {
        return new ReportTemplate(this.title, this.footer, this.watermark);
    }

    @Override
    public String toString() {
        return "Report{title=" + title + ", watermark=" + watermark + "}";
    }
}

@SuppressWarnings("unused")
final class PrototypeExample {

    private PrototypeExample() {
    }

    public static void example() {
        ReportTemplate base = new ReportTemplate("Base Report", "© Company", "Internal");

        ReportTemplate userVersion = base.copy(); // copy quickly
        userVersion.watermark = "User Copy";

        System.out.println(base);
        System.out.println(userVersion);
    }
}
