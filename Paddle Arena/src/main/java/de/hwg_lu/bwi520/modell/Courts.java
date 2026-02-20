package de.hwg_lu.bwi520.modell;

public class Courts {

    private int courtId;
    private String name;

    public Courts() {
    }

    public Courts(int courtId, String name) {
        this.courtId = courtId;
        this.name = name;
    }

    public int getCourtId() {
        return courtId;
    }

    public void setCourtId(int courtId) {
        this.courtId = courtId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Courts [courtId=" + courtId + ", name=" + name + "]";
    }
}
