/*
 * MenuController.java
 *
 * Created on Oct 28, 2007, 11:51:58 AM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ui.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import ui.ImageBrowser;

/**
 * Classe MenuController s'occupe des actions des menus
 * 
 * @author Olivier Bilodeau <olivier.bilodeau.1@gmail.com>, Kim Lebel
 *         <lebel.kim@gmail.com>, Jean-Philippe Plante
 *         <jphilippeplante@gmail.com>, Francois Proulx
 *         <francois.proulx@gmail.com>
 */
public class MenuController {

	/**
	 * Ouverture d'une image
	 * 
	 * @param browser est le conteneur d'images pour l'ajouter
	 * @return l'image ouverte
	 */
	public BufferedImage openFile(ImageBrowser browser) {

		BufferedImage image = null;

		//creation du filechooser pour ouvrir une image
		String filename = File.separator + "Users"; //TODO path default...
		JFileChooser fc = new JFileChooser(new File(filename));
		fc.showOpenDialog(null);

		try {
			//Lire l'image avec un ImageIO et l'ajouter au image browser
			File file = fc.getSelectedFile();
			if (file.exists() && file.canRead() && file.isFile()) {
				image = ImageIO.read(file);
				browser.addImage(image, file.getCanonicalPath());
			}
		} catch (Exception e) {
			//message d'erreur
			JOptionPane.showMessageDialog(null,
				    "Error while open the image.",
				    "Error while opening",
				    JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		return image;
	}

	/**
	 * Sauvegarde de l'image sur le disque
	 * 
	 * @param image a sauvegard� sur le disque
	 */
	public void saveFile(BufferedImage image) {
		//creation du filechooser pour sauvegarder une image
		String filename = File.separator + "Users";
		JFileChooser fc = new JFileChooser(filename);
		
		//changer le type de dialog pour la sauvegarde
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setMultiSelectionEnabled(false);
		fc.setDialogTitle("Untitled");

		//code de retour
		int returnVal = fc.showSaveDialog(null);

		try {
			//si ok sauvegarder l'image avec le nom
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File myfile = fc.getSelectedFile();
				ImageIO.write(image, "jpg", myfile);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
				    "Error while saving the new image.",
				    "Error while saving",
				    JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}
}