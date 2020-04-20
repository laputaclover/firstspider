package bean;

public class CourtInfoBean {
    private String domain, area, name, uid;
    private int price, age,buildingNum,onSaleNum, done90,porspecting30;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(int buildingNum) {
        this.buildingNum = buildingNum;
    }

    public int getOnSaleNum() {
        return onSaleNum;
    }

    public void setOnSaleNum(int onSaleNum) {
        this.onSaleNum = onSaleNum;
    }

    public int getDone90() {
        return done90;
    }

    public void setDone90(int done90) {
        this.done90 = done90;
    }

    public int getPorspecting30() {
        return porspecting30;
    }

    public void setPorspecting30(int porspecting30) {
        this.porspecting30 = porspecting30;
    }

    public CourtInfoBean() {
    }

    @Override
    public String toString() {
        return
                "" + uid  +
                "," + domain  +
                "," + area  +
                "," + name  +
                "," + price +
                "," + age +
                "," + buildingNum +
                "," + onSaleNum +
                "," + done90 +
                "," + porspecting30;
    }
}
