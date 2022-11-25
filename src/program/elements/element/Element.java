package program.elements.element;

import program.elements.drawable.Drawable;

import java.awt.*;

public abstract class Element implements Drawable {
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

    public void changeActiveStatus() {
        if (this.hasStatus(ElementStatus.ACTIVE)) {
            this.elementStatus = ElementStatus.NONE;
        } else {
            this.elementStatus = ElementStatus.ACTIVE;
        }
    }

    public abstract boolean containsPoint(Point point);

    public void setElementStatus(ElementStatus elementStatus) {
        this.elementStatus = elementStatus;
    }

    public boolean hasStatus(ElementStatus elementStatus) {
        return this.elementStatus == elementStatus;
    }

    public boolean exists() {
        return this.elementStatus != ElementStatus.EMPTY_ELEMENT;
    }

    @Override
    public String toString() {
        return this.elementName;
    }
}
