package program.elements.element;

import program.elements.drawable.Drawable;

import java.awt.*;

public abstract class Element implements Drawable {
    protected final int id;
    protected String elementName;
    protected Status status;

    public Element(int id, String elementName, Status status) {
        this.id = id;
        this.elementName = elementName;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getElementName() {
        return elementName;
    }

    public void changeActiveStatus() {
        if (this.hasStatus(Status.ACTIVE)) {
            this.status = Status.NONE;
        } else {
            this.status = Status.ACTIVE;
        }
    }

    public abstract boolean containsPoint(Point point);

    public void setElementStatus(Status status) {
        this.status = status;
    }

    public boolean hasStatus(Status status) {
        return this.status == status;
    }

    public boolean exists() {
        return this.status != Status.EMPTY_ELEMENT;
    }

    public abstract boolean isTrustful();

    @Override
    public String toString() {
        return this.elementName;
    }
}
