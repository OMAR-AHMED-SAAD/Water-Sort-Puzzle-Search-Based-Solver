package code;

public class Bottle {
    public char[] layers;
    private int numEmpty;
    private char topLayer;
    private int topLayerIndex;

    public Bottle(String bottleLayers) {
        this.layers = bottleLayers.replaceAll(",", "").toCharArray();
        this.topLayer = 'e';
        this.topLayerIndex = layers.length - 1;
        for (char layer : this.layers) {
            if (layer == 'e')
                this.numEmpty++;
            if (layer != 'e') {
                this.topLayer = layer;
                this.topLayerIndex = this.numEmpty;
                break;
            }
        }
    }

    private boolean isFull() {
        return this.numEmpty == 0;
    }

    private boolean isEmpty() {
        return this.numEmpty == this.layers.length;
    }

    public boolean isAllLayersSame() {
        for (char layer : this.layers) {
            if (layer != this.topLayer && layer != 'e')
                return false;
        }
        return true;
    }

    private void addLayer(char layer) {
        this.numEmpty--;
        this.layers[this.numEmpty] = layer;
        this.topLayer = layer;
        this.topLayerIndex = this.numEmpty;
    }

    private void removeTopLayer() {
        this.layers[this.topLayerIndex] = 'e';
        this.numEmpty++;
        if (this.numEmpty == this.layers.length)
            this.topLayerIndex = this.layers.length - 1;
        else
            this.topLayerIndex++;
        this.topLayer = layers[this.topLayerIndex];
    }

    public boolean isPourValid(Bottle bottle) {
        return !(bottle.isFull() || this.isEmpty() || (!bottle.isEmpty() && this.topLayer != bottle.topLayer));
    }

    public int pourTo(Bottle bottle) {
        int topMostSameLayer = 0;
        // count the topmost layer that is the same in the sender
        for (int i = topLayerIndex; i < this.layers.length; i++) {
            if (this.layers[i] != this.topLayer)
                break;
            topMostSameLayer++;
        }
        int numOfPours = Math.min(bottle.numEmpty, topMostSameLayer);

        for (int i = 0; i < numOfPours; i++) {
            bottle.addLayer(topLayer);
            this.removeTopLayer();
        }
        return numOfPours;

    }

    public int layersRemToSameColor() {
        int h = 0;
        char bottomLayer = this.layers[this.layers.length - 1];

        for (int i = 0; i < this.layers.length; i++) {
            if (this.layers[i] != bottomLayer && this.layers[i] != 'e')
                h++;
        }
        return h;
    }

    @Override
    public String toString() {
        return new String(this.layers);
    }

    @Override
    public boolean equals(Object obj) {
        Bottle bottle = (Bottle) obj;

        for (int i = 0; i < this.layers.length; i++)
            if (this.layers[i] != bottle.layers[i])
                return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (int i = 0; i < this.layers.length; i++)
            hash += this.layers[i] * (i + 1) * 31;
        return hash;
    }

    public char getTopLayer() {
        return topLayer;
    }
}