import java.awt.*;

public abstract class Element implements Drawable{
    protected final int id;
    protected String elementName;
    protected ElementStatus elementStatus;

    public Element(int id, String elementName, ElementStatus elementStatus) {
        this.id = id;
        this.elementName = elementName;
        this.elementStatus = elementStatus;
    }

    public int getId() {
        return id;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public ElementStatus getElementStatus() {
        return elementStatus;
    }

    public void setElementStatus(ElementStatus elementStatus) {
        this.elementStatus = elementStatus;
    }

    public boolean hasStatus(ElementStatus elementStatus) {
        return this.elementStatus == elementStatus;
    }
}
