package com.kaskys.speedreadinginformation.app.text;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

public class ImageUtils {
	
	public static List<ImagePiece> split(Bitmap bitmap,int xPiece,int yPiece){
		List<ImagePiece> pieces = new ArrayList<ImagePiece>(xPiece*yPiece);
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int pieceWidth = width / xPiece;
        int pieceHeight = height / yPiece;
        for(int i=0;i<yPiece;i++){
        	for(int k=0;k<xPiece;k++){
        		ImagePiece piece = new ImagePiece();
                piece.index = k + i * xPiece;
                int xValue = k * pieceWidth;
                int yValue = i * pieceHeight;
                piece.bitmap = Bitmap.createBitmap(bitmap, xValue, yValue,
                        pieceWidth, pieceHeight);
                pieces.add(piece);
        	}
        }
		return pieces;
	}
}
