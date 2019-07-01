package tu.wms.inventorying.domain;

import java.util.Objects;

public class InventoryId {

    private Long coordX;
    private Long coordY;
    private Long coordZ;
    private Long goodsId;

    public InventoryId() {

    }

    public InventoryId(Long coordX, Long coordY, Long coordZ, Long goodsId) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.coordZ = coordZ;
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return "InventoryId{" +
                "coordX=" + coordX +
                ", coordY=" + coordY +
                ", coordZ=" + coordZ +
                ", goodsId=" + goodsId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InventoryId that = (InventoryId) o;
        return Objects.equals(coordX, that.coordX) &&
                Objects.equals(coordY, that.coordY) &&
                Objects.equals(coordZ, that.coordZ) &&
                Objects.equals(goodsId, that.goodsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordX, coordY, coordZ, goodsId);
    }

    public Long getCoordX() {
        return coordX;
    }

    public void setCoordX(Long coordX) {
        this.coordX = coordX;
    }

    public Long getCoordY() {
        return coordY;
    }

    public void setCoordY(Long coordY) {
        this.coordY = coordY;
    }

    public Long getCoordZ() {
        return coordZ;
    }

    public void setCoordZ(Long coordZ) {
        this.coordZ = coordZ;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}
