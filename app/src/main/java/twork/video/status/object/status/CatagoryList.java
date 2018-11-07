package twork.video.status.object.status;

import android.os.Parcel;
import android.os.Parcelable;

public class CatagoryList implements Parcelable {
    public static final Creator<CatagoryList> CREATOR = new C03541();
    private String category;
    private int categoryid;

    public CatagoryList() {

    }

    static class C03541 implements Creator<CatagoryList> {
        C03541() {
        }

        public CatagoryList createFromParcel(Parcel source) {
            return new CatagoryList(source);
        }

        public CatagoryList[] newArray(int size) {
            return new CatagoryList[size];
        }
    }

    public int getCategoryid() {
        return this.categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.categoryid);
        dest.writeString(this.category);
    }

    public CatagoryList(Parcel in) {
        this.categoryid = in.readInt();
        this.category = in.readString();
    }
}
