package com.pv.builder;

import java.awt.*;

public class GridBagConstraintsBuilder {

    public static class Builder {
        private int gridX;
        private int gridY;
        private int gridWidth;
        private int gridHeight;
        private double weightX;
        private double weightY;
        private int fill;
        private int anchor;

        public Builder() {
            gridX = 0;
            gridY = 0;
            gridWidth = 0;
            gridHeight = 0;
            weightX = 0.0;
            weightY = 0.0;
            fill = GridBagConstraints.NONE;
            anchor = GridBagConstraints.CENTER;
        }

        public Builder setGridX(int gridX) {
            this.gridX = gridX;
            return this;
        }

        public Builder setGridY(int gridY) {
            this.gridY = gridY;
            return this;
        }

        public Builder setGridWidth(int gridWidth) {
            this.gridWidth = gridWidth;
            return this;
        }

        public Builder setGridHeight(int gridHeight) {
            this.gridHeight = gridHeight;
            return this;
        }

        public Builder setWeightX(double weightX) {
            this.weightX = weightX;
            return this;
        }

        public Builder setWeightY(double weightY) {
            this.weightY = weightY;
            return this;
        }

        public Builder setFill(int fill) {
            this.fill = fill;
            return this;
        }

        public Builder setAnchor(int anchor) {
            this.anchor = anchor;
            return this;
        }

        public GridBagConstraints build() {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = this.gridX;
            gbc.gridy = this.gridY;
            gbc.gridwidth = this.gridWidth;
            gbc.gridheight = this.gridHeight;
            gbc.weightx = this.weightX;
            gbc.weighty = this.weightY;
            gbc.fill = this.fill;
            gbc.anchor = this.anchor;
            return gbc;
        }
    }
}
