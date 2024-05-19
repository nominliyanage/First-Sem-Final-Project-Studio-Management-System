package lk.ijse.studio.model.tm;

public class PackageTm {
    private String package_id;
    private String package_name;
    private String price;
    private String includes;

    public PackageTm() {
    }

    public PackageTm(String package_id, String package_name, String price, String includes) {
        this.package_id = package_id;
        this.package_name = package_name;
        this.price = price;
        this.includes = includes;
    }

    public String getPackageId() {
        return package_id;
    }

    public void setPackageId(String package_id) {
        this.package_id = package_id;
    }

    public String getPackageName() {
        return package_name;
    }

    public void setPackageName(String package_name) {
        this.package_name = package_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIncludes() {
        return includes;
    }

    public void setIncludes(String includes) {
        this.includes = includes;
    }

    @Override
    public String toString() {
        return "PackageTm{" +
                "package_id='" + package_id + '\'' +
                ", package_name='" + package_name + '\'' +
                ", price='" + price + '\'' +
                ", includes='" + includes + '\'' +
                '}';
    }
}
