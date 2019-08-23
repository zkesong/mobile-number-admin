package cn.qydx.hmdj.dto;

public class PhoneNumberDto {
    private int id;
    private String name;
    private String longNumber;
    private String shortNumber;
    private String customManager;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongNumber() {
        return longNumber;
    }

    public void setLongNumber(String longNumber) {
        this.longNumber = longNumber;
    }

    public String getShortNumber() {
        return shortNumber;
    }

    public void setShortNumber(String shortNumber) {
        this.shortNumber = shortNumber;
    }

    public String getCustomManager() {
        return customManager;
    }

    public void setCustomManager(String customManager) {
        this.customManager = customManager;
    }
}
