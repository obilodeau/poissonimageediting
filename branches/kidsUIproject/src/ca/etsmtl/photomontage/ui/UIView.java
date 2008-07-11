/*
 * SmartPhotomontage
 * Copyright (C) 2007
 * François Proulx, Olivier Bilodeau, Jean-Philippe Plante, Kim Lebel
 * http://poissonimageediting.googlecode.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package ca.etsmtl.photomontage.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

import org.jdesktop.application.Action;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.SingleFrameApplication;

import ca.etsmtl.photomontage.ui.containers.ImageFramesContainer;
import ca.etsmtl.photomontage.ui.containers.WindowItem;
import ca.etsmtl.photomontage.ui.controllers.MenuController;

import com.developpez.gfx.swing.drag.GhostGlassPane;

/**
 * The application's main frame.
 */
public class UIView extends FrameView implements Observer {
	
	// Variables declaration
	protected static javax.swing.JMenu WindowsMenu;
	private javax.swing.JPanel browser;
	private javax.swing.JPanel mainPanel;
	private javax.swing.JDesktopPane mdi;
	private javax.swing.JMenuBar menuBar;
	private javax.swing.JMenuItem openFileMenuItem;
	private javax.swing.JPanel rightpanel;
	private javax.swing.JMenuItem saveMenuItem;

	private JDialog aboutBox;

	// image frame container
	private ImageFramesContainer container = new ImageFramesContainer();

	// ca.etsmtl.photomontage.ui menu controller
	private MenuController menuCtrl = new MenuController();

	// image browser
	private ImageBrowser imagebrowser;
	
	private SelectionBrowser selectionBrowser;

	
	/**
	 * Construtor for the main UIView
	 * @param app
	 */
	public UIView(SingleFrameApplication app) {
		super(app);
		
		// initialisation de tous les components
		initComponents();
	}

	/**
	 * Show About Box for our application
	 * 
	 * @param e
	 */
	@Action
	public void showAboutBox(ActionEvent e) {
		if (aboutBox == null) {
			JFrame mainFrame = UIApp.getApplication().getMainFrame();
			aboutBox = new UIAboutBox(mainFrame);
			aboutBox.setLocationRelativeTo(mainFrame);
		}
		UIApp.getApplication().show(aboutBox);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 * 
	 * HAHA too late!... ... :'(
	 */
	private void initComponents() {
		
		// Load resource file
		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
		.getInstance(ca.etsmtl.photomontage.ui.UIApp.class).getContext().getResourceMap(
				UIView.class);		
		
		GhostGlassPane glassPane = new GhostGlassPane();
        getFrame().setGlassPane(glassPane);
		
		// Our code 
		//TODO: REfactor me please !
		
		// creation du image browser
		ImageBrowser.currentSize = 180;

		// ajouter UIView en temps que observer pour les containers de image
		// frame
		container.addObserver(this);
		
		// initialize major UI components
		mainPanel = new javax.swing.JPanel();
		mdi = new javax.swing.JDesktopPane();
		browser = new javax.swing.JPanel();
		rightpanel = new javax.swing.JPanel();
		
		// Menu code
		// ---------
		// Let's try to do each menu from left to right
		// TODO reimplement Accelerators for powerusers w/o having the shortcut showing up
		
		menuBar = new javax.swing.JMenuBar();
		menuBar.setName("menuBar"); // NOI18N

		// Open
		openFileMenuItem = new javax.swing.JMenuItem();
		openFileMenuItem.setIcon(new ImageIcon(resourceMap.getString("fileMenu.icon")));
		openFileMenuItem.setName("openFileMenuItem"); // NOI18N
		openFileMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				openFileMenuItemActionPerformed(evt);
			}
		});
		menuBar.add(openFileMenuItem);

		// Save
		// TODO I'm here. Make this a toplevel menu with Icon and two sub icons
		saveMenuItem = new javax.swing.JMenuItem();
		saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_S,
				java.awt.event.InputEvent.CTRL_MASK));
		saveMenuItem.setText(resourceMap.getString("saveMenuItem.text")); // NOI18N
		saveMenuItem.setName("saveMenuItem"); // NOI18N
		saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveMenuItemActionPerformed(evt);
			}
		});
		menuBar.add(saveMenuItem);

		// Quit
		javax.swing.JMenuItem quitMenuItem = new javax.swing.JMenuItem();
		quitMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				quitMenuItemActionPerformed(evt);
			}
		});
		quitMenuItem.setIcon(new ImageIcon(resourceMap.getString("quitMenuItem.icon"))); // NOI18N

		menuBar.add(quitMenuItem);

		// Help menu
		javax.swing.JMenu helpMenu = new javax.swing.JMenu();
		helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
		helpMenu.setName("helpMenu"); // NOI18N

		// TODO we should get rid of these "NetBeans"ries UIView.form and all these helper classes that makes code unreadable
		javax.swing.ActionMap actionMap = org.jdesktop.application.Application
		.getInstance(ca.etsmtl.photomontage.ui.UIApp.class).getContext().getActionMap(
				UIView.class, this);
		
		// About
		javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
		aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
		aboutMenuItem.setName("aboutMenuItem"); // NOI18N
		helpMenu.add(aboutMenuItem);
		
		menuBar.add(helpMenu);
		
		
		// Layout and panel setup
		// ----------------------
		
		mainPanel.setName("mainPanel"); // NOI18N

		browser.setAutoscrolls(true);
		browser.setName("browser"); // NOI18N
		browser.setPreferredSize(new java.awt.Dimension(200, 0));

		org.jdesktop.layout.GroupLayout browserLayout = new org.jdesktop.layout.GroupLayout(
				browser);
		browser.setLayout(browserLayout);
		browserLayout.setHorizontalGroup(browserLayout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(0, 200,
				Short.MAX_VALUE));
		browserLayout.setVerticalGroup(browserLayout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(0, 380,
				Short.MAX_VALUE));

		mdi.setName("mdi"); // NOI18N

		rightpanel.setName("rightpanel"); // NOI18N

		org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		
		mainPanelLayout.setHorizontalGroup(mainPanelLayout
						.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(mainPanelLayout
										.createSequentialGroup()
										.add(browser,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(mdi)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
										.add(rightpanel,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)));
		mainPanelLayout.setVerticalGroup(mainPanelLayout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(rightpanel,
				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.add(org.jdesktop.layout.GroupLayout.TRAILING, mdi).add(
						browser, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
						380, Short.MAX_VALUE));

		setComponent(mainPanel);
		setMenuBar(menuBar);

		// paramétriser le imageframe et ajouter à l'interface
		imagebrowser = new ImageBrowser(container);
		JScrollPane imageBrowserScrollpane = new JScrollPane(imagebrowser);
		imageBrowserScrollpane.setLayout(new ScrollPaneLayout());
		imageBrowserScrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		imageBrowserScrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		imageBrowserScrollpane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);

		browser.setLayout(new BorderLayout());
		browser.add(imageBrowserScrollpane);
		
		// paramétriser le imageframe et ajouter à l'interface
		selectionBrowser = new SelectionBrowser();
		JScrollPane selectionBrowserScrollpane = new JScrollPane(selectionBrowser );
		selectionBrowserScrollpane.setLayout(new ScrollPaneLayout());
		selectionBrowserScrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		selectionBrowserScrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		selectionBrowserScrollpane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
		rightpanel.setLayout(new BorderLayout());
		rightpanel.add(selectionBrowserScrollpane);
	}

	private void openFileMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
		menuCtrl.openFile(imagebrowser);
	}

	private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
		if (mdi.getSelectedFrame() != null) {
			new Thread() {
				public void run() {
					//saving...
					ImageFrame f = (ImageFrame) mdi.getSelectedFrame();
					String path = menuCtrl.saveFile(f.getImageHolder().getImage());
					
					//set path and change image state
					f.getImageHolder().setFilename(path);
					f.setModified(false);
				}
			}.start();	
		}
	}
	
	private void quitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
		UIApp.getApplication().exit();
	}

	/**
	 * Update des observers
	 */
	public void update(Observable arg0, Object arg1) {

		// update pour le container de imageframe
		if (arg0 instanceof ImageFramesContainer) {
			// keep a copy of the components
			ArrayList<ImageFrame> mdiframes = new ArrayList<ImageFrame>();

			// remove old frames
			if (mdi.getComponents().length > 0) {
				for (int i = 0; i < mdi.getComponents().length; i++) {
					if (mdi.getComponent(i) instanceof ImageFrame) {
						ImageFrame frame = (ImageFrame) mdi.getComponent(i);	
						if (!container.contains(frame)) {
							mdi.remove(frame);
						} else {
							mdiframes.add(frame);
						}
					}
				}
			}

			// add new frames
			for (ImageFrame frame : container.getFrames()) {
				if (!mdiframes.contains(frame)) {
					// ajouter item dans le menu window
					frame.setMenuItem(new WindowItem(frame, mdi));
					UIView.WindowsMenu.add(frame.getMenuItem());

					// ajouter le frame dans le desktop et activer le frame
					mdi.add(frame);
					mdi.getDesktopManager().activateFrame(frame);
				}
			}

			// clean mdi frames
			mdiframes = null;
		}
	}

	/**
	 * 
	 * @return the selection browser component
	 */
	public SelectionBrowser getSelectionBrowser() {
		return selectionBrowser;
	}

	/**
	 * 
	 * @return the mdi component
	 */
	public javax.swing.JDesktopPane getImageFramesDesktop() {
		return mdi;
	}
}