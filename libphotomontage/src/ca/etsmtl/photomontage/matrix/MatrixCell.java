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

package ca.etsmtl.photomontage.matrix;

/**
 * MatrixCell is representing a cell in a matrix
 *
 */
public class MatrixCell {
	/**
	 * row number
	 */
	public final int row;
	/**
	 * col number
	 */
	public final int col;
	/**
	 * rgba value
	 */
	public final int value;
	
	/**
	 * Constructor
	 * @param row number
	 * @param col number
	 * @param value for rgba
	 */
	public MatrixCell(final int row, final int col, final int value) {
		this.row = row;
		this.col = col;
		this.value = value;
	}
}