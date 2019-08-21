package com.nengyuanbox.repaircar.bean;

public class RepairTypeBean {

    private String  type;
    private String  id;
    public boolean checked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RepairTypeBean(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public RepairTypeBean(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RepairTypeBean{" +
                "type='" + type + '\'' +
                ", checked=" + checked +
                '}';
    }
}
