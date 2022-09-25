package game.actor;

import math.Attachable;
import java.awt.Color;

import math.Node;
import math.Vector;
import window.Canvas;

/**
 * Contains information to render a single string, which can be attached to any positionable.
 */
public class TextGraphics extends Node implements Attachable, Graphics {
    
	private String text;
    private float fontSize;
	private Color fillColor;
	private Color outlineColor;
    private float thickness;
	private boolean bold;
	private boolean italics;
    private Vector anchor;
	private float alpha;
	private float depth;

    /**
     * Creates a new text graphics.
     * @param text content, not null
     * @param fontSize size
     * @param fillColor fill color, may be null
     * @param outlineColor outline color, may be null
     * @param thickness outline thickness
     * @param bold whether to use bold font
     * @param italics whether to use italics font
     * @param anchor text anchor
     * @param alpha transparency, between 0 (invisible) and 1 (opaque)
     * @param depth render priority, lower-values drawn first
     */
    public TextGraphics(
            String text,
            float fontSize,
            Color fillColor,
            Color outlineColor,
            float thickness,
            boolean bold,
            boolean italics,
            Vector anchor,
            float alpha,
            float depth
    ) {
        if (text == null) {
            throw new NullPointerException();
        }
        this.text = text;
        this.fontSize = fontSize;
        this.fillColor = fillColor;
        this.outlineColor = outlineColor;
        this.thickness = thickness;
        this.bold = bold;
        this.italics = italics;
        this.anchor = anchor;
        this.alpha = alpha;
        this.depth = depth;
    }

    /**
     * Creates a new text graphics.
     * @param text content, not null
     * @param fontSize size
     * @param fillColor fill color, may be null
     * @param outlineColor outline color, may be null
     * @param thickness outline thickness
     * @param bold whether to use bold font
     * @param italics whether to use italics font
     * @param anchor text anchor
     */
    public TextGraphics(
            String text,
            float fontSize,
            Color fillColor,
            Color outlineColor,
            float thickness,
            boolean bold,
            boolean italics,
            Vector anchor
    ) {
        this(text, fontSize, fillColor, outlineColor, thickness, bold, italics, anchor, 1.0f, 0.0f);
    }
    
    /**
     * Creates a new text graphics.
     * @param text content, not null
     * @param fontSize size
     * @param fillColor fill color, may be null
     */
    public TextGraphics(String text, float fontSize, Color fillColor) {
        this(text, fontSize, fillColor, null, 0.0f, false, false, Vector.ZERO);
    }

    /**
     * Sets text content.
     * @param text content, not null
     */
    public void setText(String text) {
        if (text == null)
            throw new NullPointerException();
        this.text = text;
    }

    /**
     * Sets fill color.
     * @param fillColor color, may be null
     */
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

    /**
     * Sets text anchor, i.e. how to orient it.
     * @param anchor text anchor
     */
    public void setAnchor(Vector anchor) {
        this.anchor = anchor;
    }

    /** @return text anchor */
    public Vector getAnchor() {
        return anchor;
    }
    
	/**
     * Sets transparency.
     * @param alpha transparency, between 0 (invisible) and 1 (opaque)
     */
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    /** @return transparency, between 0 (invisible) and 1 (opaque) */
    public float getAlpha() {
        return alpha;
    }

    /**
     * Sets rendering depth.
     * @param depth render priority, lower-values drawn first
     */
    public void setDepth(float depth) {
        this.depth = depth;
    }

    /** @return render priority, lower-values drawn first */
    public float getDepth() {
        return depth;
    }

	@Override
	public void draw(Canvas canvas) {
        canvas.drawText(
                text,
                fontSize,
                getTransform(),
                fillColor,
                outlineColor,
                thickness,
                bold,
                italics,
                anchor,
                alpha,
                depth
        );
	}

}
