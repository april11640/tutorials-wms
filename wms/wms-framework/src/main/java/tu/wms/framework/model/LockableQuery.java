package tu.wms.framework.model;

public class LockableQuery {

    protected boolean enableLock;

    public <T extends LockableQuery> T withLock() {
        enableLock = true;
        return (T)this;
    }

    public boolean isEnableLock() {
        return enableLock;
    }

    public void setEnableLock(boolean enableLock) {
        this.enableLock = enableLock;
    }

}
