package de.lessvoid.nifty.java2d.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.lessvoid.nifty.NiftyInputConsumer;
import de.lessvoid.nifty.input.keyboard.KeyboardInputEvent;
import de.lessvoid.nifty.spi.input.InputSystem;
import de.lessvoid.nifty.tools.resourceloader.NiftyResourceLoader;

public class InputSystemAwtImpl implements InputSystem, MouseMotionListener,
		MouseListener, KeyListener {

	private ConcurrentLinkedQueue<MouseEvent> mouseEvents = new ConcurrentLinkedQueue<MouseEvent>();

	private ConcurrentLinkedQueue<KeyboardInputEvent> keyboardEvents = new ConcurrentLinkedQueue<KeyboardInputEvent>();

  @Override
  public void setResourceLoader(final NiftyResourceLoader nifty) {
  }

	@Override
	public void forwardEvents(final NiftyInputConsumer inputEventConsumer) {
	  MouseEvent mouseEvent = mouseEvents.poll();
		while (mouseEvent != null) {
			inputEventConsumer.processMouseEvent(mouseEvent.getX(), mouseEvent.getY(), 0, mouseEvent.getButton() - 1, mouseEvent.getButton() != MouseEvent.NOBUTTON);
			mouseEvent = mouseEvents.poll();
		}

		KeyboardInputEvent keyEvent = keyboardEvents.poll();
		while (keyEvent != null) {
			inputEventConsumer.processKeyboardEvent(keyEvent);
			keyEvent = keyboardEvents.poll();
		}
	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) {
    mouseEvents.add(mouseEvent);
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		mouseEvents.add(mouseEvent);
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {

	}

	@Override
  public void mousePressed(MouseEvent mouseEvent) {
	  // at the moment we only care about the BUTTON1
	  if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
	    mouseEvents.add(mouseEvent);
	  }
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
	  // at the moment we only care about the BUTTON1
	  if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
	    mouseEvents.add(mouseEvent);
	  }
	}

	@Override
	public void keyPressed(KeyEvent e) {
		handleKeyEvent(e, true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		handleKeyEvent(e, false);
	}

	private void handleKeyEvent(KeyEvent e, boolean isKeyDown) {
		int newKeyCode = keyCodeConverter.convertToNiftyKeyCode(e.getKeyCode(), e.getKeyLocation());
		keyboardEvents.add(new KeyboardInputEvent(newKeyCode, e
				.getKeyChar(), isKeyDown, e.isShiftDown(), e.isControlDown()));
	}

	AwtToNiftyKeyCodeConverter keyCodeConverter = new AwtToNiftyKeyCodeConverter();

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

  @Override
  public void setMousePosition(int x, int y) {
    // TODO Auto-generated method stub
  }
}