package pl.shop.lab.model;

public class DigitalProduct extends Product {

    private String platform;
    private String licenseKey;
    private String downloadUrl;

    public DigitalProduct(String id, String name,String description, double price, String imageUrl,
                          String platform, String licenseKey, String downloadUrl) {
        super(id, name,description, price, imageUrl);
        this.platform = platform;
        this.licenseKey = licenseKey;
        this.downloadUrl = downloadUrl;
    }

    public String getPlatform() { return platform; }
    public String getLicenseKey() { return licenseKey; }
    public String getDownloadUrl() { return downloadUrl; }

    @Override
    public String getType() {
        return "DIGITAL";
    }

    @Override
    public String toCsv() {
        return String.join(";",
                getType(),
                getId(),
                getName(),
                String.valueOf(getPrice()),
                getImageUrl(),
                platform,
                licenseKey,
                downloadUrl
        );
    }
}
