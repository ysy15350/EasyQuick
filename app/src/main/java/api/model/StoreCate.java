package api.model;

/**
 * Created by yangshiyou on 2017/10/9.
 */

public class StoreCate {
//    {"code":200,"result":[
// {"id":"5","title":"\u670d\u52a1"},

    private int id;
    private String title;

    private boolean isChecked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
