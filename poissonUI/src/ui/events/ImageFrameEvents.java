/*
 * ImageFrameEvent.java
 *
 * Created on Oct 28, 2007, 12:59:46 AM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ui.events;

import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import ui.ImageFrame;
import ui.containers.PreviewContainer;

/**
 * @author Olivier Bilodeau <olivier.bilodeau.1@gmail.com>, Kim Lebel
 *         <lebel.kim@gmail.com>, Jean-Philippe Plante
 *         <jphilippeplante@gmail.com>, Francois Proulx
 *         <francois.proulx@gmail.com>
 */
public class ImageFrameEvents implements InternalFrameListener {

	private ImageFrame frame;

	private PreviewContainer preview;

	public ImageFrameEvents(ImageFrame frame, PreviewContainer preview) {
		this.frame = frame;
		this.preview = preview;
	}

	public void internalFrameOpened(InternalFrameEvent arg0) {
		// nothing...
	}

	public void internalFrameClosing(InternalFrameEvent arg0) {
		// TODO ask save
	}

	public void internalFrameClosed(InternalFrameEvent arg0) {
		frame.close();
	}

	public void internalFrameIconified(InternalFrameEvent arg0) {
		throw new UnsupportedOperationException("Not supported.");
	}

	public void internalFrameDeiconified(InternalFrameEvent arg0) {
		throw new UnsupportedOperationException("Not supported.");
	}

	public void internalFrameActivated(InternalFrameEvent arg0) {
		preview.update(frame.getImage().getScaledImage());
	}

	public void internalFrameDeactivated(InternalFrameEvent arg0) {
		preview.update(null);
	}
}