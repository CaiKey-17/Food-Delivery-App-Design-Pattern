package com.example.project_android.Model;

public class GroupDish {
    private String name;
    private String image;

    // Đổi tên biến thành 'name' (viết thường)

    public GroupDish() {
    }

    public GroupDish(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public GroupDish(String name) {
        this.name = name; // Sử dụng 'this.name' để ánh xạ đúng
    }

    public String getName() {
        return name; // Trả về biến 'name'
    }

    public void setName(String name) {
        this.name = name; // Sử dụng 'this.name' để ánh xạ đúng
    }
}
