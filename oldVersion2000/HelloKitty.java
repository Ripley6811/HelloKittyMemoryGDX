import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.*;

/**
 * Author: Jay W Johnson
 * Date: 8/9/2000
 */

public class HelloKitty extends JApplet implements MouseListener,
						   MouseMotionListener {
  Container c;
  Graphics g, offG;
  Image offScreenImage;
  JButton start;
  Image startScreen, kittyBow, kittyFlowersMini,
	kittyFWave1, kittyFWave2, board,
        open0, open1, open2, open3, open4;
  Image roseA, redBowA,
	roseB, redBowB;
  int x, y;
  int saveX, saveY;
  int saveInd1, saveInd2;
  Image saveI;
  int gameScene = 0;
  boolean twoCards = true;
  Image[][] imageGrid;
  boolean[][] isFilled;
  boolean[][] isHidden;
  String[] gifs;

  public void init() {
    setSize( 500, 400 );
    g = getGraphics();
    c = getContentPane();
    c.setLayout( null );
    c.setVisible( true );
    c.addMouseListener( this );
    c.addMouseMotionListener( this );
    offScreenImage = createImage( 500, 400 );
    offG = offScreenImage.getGraphics();

    imageGrid = new Image[8][6];
    isFilled = new boolean[8][6];
    isHidden = new boolean[8][6];
    for ( int i = 0; i < 8; i++ ) {
      for ( int j = 0; j < 6; j++ ) {
        isFilled[i][j] = false;
        isHidden[i][j] = true;
      }
    }

    startScreen = getImage( getDocumentBase(), "startScreen.gif" );
    board = getImage( getDocumentBase(), "board.gif" );
    kittyBow = getImage( getDocumentBase(), "kittyBow.gif" );
    kittyFlowersMini = getImage( getDocumentBase(), "kittyFlowersMini.gif" );
    kittyFWave1 = getImage( getDocumentBase(), "kittyFWave1.gif" );
    kittyFWave2 = getImage( getDocumentBase(), "kittyFWave2.gif" );
    open0 = getImage( getDocumentBase(), "open0.gif" );
    open1 = getImage( getDocumentBase(), "open1.gif" );
    open2 = getImage( getDocumentBase(), "open2.gif" );
    open3 = getImage( getDocumentBase(), "open3.gif" );
    open4 = getImage( getDocumentBase(), "open4.gif" );

    File gifDir = new File( "gifs" );
    gifs = gifDir.list();
    int rand = (int) ( Math.random() * gifs.length );
    for ( int card = 0; card < gifs.length; card++ ) {
      for ( int twoTimes = 0; twoTimes < 2; twoTimes++ ) {
        int i = (int) ( Math.random() * 8 );
        int j = (int) ( Math.random() * 6 );
        if ( isFilled[i][j] == true ) {
          twoTimes--;
          continue;
        }
        imageGrid[i][j] = getImage( getDocumentBase(), "gifs\\" + gifs[(card + rand) % gifs.length] );
        isFilled[i][j] = true;
      }
    }

  }//end INIT

  public void paint( Graphics g ) {
  ////LOAD GRAPHICS BY PRINTING OUTSIDE AREA
      g.drawImage( kittyFlowersMini, 1000, 180, this );
      g.drawImage( kittyFWave1, 1000, 180, this );
      g.drawImage( kittyFWave2, 1000, 180, this );
      g.drawImage( open0, 1000, 180, this );
      g.drawImage( open1, 1000, 180, this );
      g.drawImage( open2, 1000, 180, this );
      g.drawImage( open3, 1000, 180, this );
      g.drawImage( open4, 1000, 180, this );
      g.drawImage( board, 1000, 180, this );
      for ( int i = 0; i < 8; i++ ) {
        for ( int j = 0; j < 6; j++ ) {
          g.drawImage( imageGrid[i][j], 500 + (50*i), 50 * j, this );
        }
      }

    if ( gameScene == 0 ) g.drawImage( startScreen, 0, 0, this );

    if ( gameScene == 1 ) {
      for ( int i = 0; i < 20; i++ ) {
        offG.setColor( new Color( 255, 10 + (12 * i), 0 ));
        offG.fillRect( 0, 0, 500, 400 );
        g.drawImage( offScreenImage, 0, 0, this );
        //busy wait
        for ( int j = 0; j < 10000000; j++ );
      }
//change to 2!
      gameScene = 2;
    }

    if ( gameScene == 2 ) {
      offG.setColor( Color.yellow );
      offG.fillRect( 0, 0, 500, 400 );
      for ( int i = 0; i < 1000; i++ ) {
        offG.setColor( new Color( 255, 128, 64 ) );
        offG.drawImage( kittyFlowersMini, i - 20, 180, this );
        g.drawImage( offScreenImage, 0, 0, this );
        if ( i == 240 ) {
          gameScene = 3;
          break;
        }
        //busy wait
        for ( int j = 0; j < 200000; j++ );
        offG.setColor( Color.yellow );
        offG.fillRect( i - 20, 180, 70, 70 );
      }
      for ( int i = 0; i < 8; i++ ) {
        offG.setColor( Color.yellow );
        offG.fillRect( 220, 180, 70, 70 );
        offG.drawImage( kittyFWave1, 220, 180, this );
        g.drawImage( offScreenImage, 0, 0, this );
        //busy wait
        for ( int j = 0; j < 10000000; j++ );
        offG.setColor( Color.yellow );
        offG.fillRect( 220, 180, 70, 70 );
        offG.drawImage( kittyFWave2, 220, 180, this );
        g.drawImage( offScreenImage, 0, 0, this );
        //busy wait
        for ( int j = 0; j < 10000000; j++ );
      }
      gameScene = 3;
    }
    if ( gameScene == 3 ) {
      offG.drawImage( board, 0, 0, this );
      g.drawImage( offScreenImage, 0, 0, this );
      gameScene = 4;
    }
  }

  public void mouseClicked( MouseEvent e ) {}
  public void mousePressed( MouseEvent e ) {}
  public void mouseReleased( MouseEvent e ) {
    if ( gameScene == 0 ) {
      if ( x > 308 && x < 438 && y > 259 && y < 320 ) {
        gameScene = 1;
        paint( g );
      }
    }
    if ( gameScene == 4 ) {
      int ind1 = 0, ind2 = 0;
      for ( int i = 2; i < 500; i += 62 ) {
        if ( i > 240 && i < 260 ) i += 2;
        if ( x > i && x < i + 60 ) {
          for ( int j = 1; j < 400; j +=65 ) {
            if ( j > 190 && j < 210 ) j += 9;
            if ( y > j && y < j +65 ) {
              if ( isHidden[ind1][ind2] == false ) break;
              showCard( i, j, imageGrid[ind1][ind2] );
              isHidden[ind1][ind2] = false;
              if ( imageGrid[ind1][ind2] == saveI ) {
                twoCards = true;
                break;
              }
              if ( twoCards == true ) {
                twoCards = false;
                saveX = i; saveY = j; saveI = imageGrid[ind1][ind2];
                saveInd1 = ind1; saveInd2 = ind2;
              }
              else twoCards = true;

              if ( twoCards == true ) {
                for ( int busyWait = 0; busyWait < 20000000; busyWait++ );
                hideCard( saveX, saveY, saveI );
                isHidden[saveInd1][saveInd2] = true;
                hideCard( i, j, imageGrid[ind1][ind2] );
                isHidden[ind1][ind2] = true;
                saveX = 0; saveY = 0; saveI = null;
              }
            }
            ind2++;
          }
        }
        ind1++;
      }
    }
  }
  public void mouseEntered( MouseEvent e ) {}
  public void mouseExited( MouseEvent e ) {}
  public void mouseMoved( MouseEvent e ) {
    x = e.getX();
    y = e.getY();
    g.setColor( Color.black );
    showStatus( x + "," + y );
  }
  public void mouseDragged( MouseEvent e ) {}

  public void showCard( int x1, int y1, Image item ) {
    offG.drawImage( item, x1, y1, this );
    offG.drawImage( open1, x1, y1, this );
    g.drawImage( offScreenImage, 0, 0, this );
    for ( int i = 0; i < 20000000; i++ );
    offG.drawImage( item, x1, y1, this );
    offG.drawImage( open2, x1, y1, this );
    g.drawImage( offScreenImage, 0, 0, this );
    for ( int i = 0; i < 20000000; i++ );
    offG.drawImage( item, x1, y1, this );
    offG.drawImage( open3, x1, y1, this );
    g.drawImage( offScreenImage, 0, 0, this );
    for ( int i = 0; i < 20000000; i++ );
    offG.drawImage( item, x1, y1, this );
    offG.drawImage( open4, x1, y1, this );
    g.drawImage( offScreenImage, 0, 0, this );
    for ( int i = 0; i < 20000000; i++ );
  }
  public void hideCard( int x1, int y1, Image item ) {
    offG.drawImage( item, x1, y1, this );
    offG.drawImage( open3, x1, y1, this );
    g.drawImage( offScreenImage, 0, 0, this );
    for ( int i = 0; i < 20000000; i++ );
    offG.drawImage( item, x1, y1, this );
    offG.drawImage( open2, x1, y1, this );
    g.drawImage( offScreenImage, 0, 0, this );
    for ( int i = 0; i < 20000000; i++ );
    offG.drawImage( item, x1, y1, this );
    offG.drawImage( open1, x1, y1, this );
    g.drawImage( offScreenImage, 0, 0, this );
    for ( int i = 0; i < 20000000; i++ );
    offG.drawImage( item, x1, y1, this );
    offG.drawImage( open0, x1, y1, this );
    g.drawImage( offScreenImage, 0, 0, this );
    for ( int i = 0; i < 20000000; i++ );
  }




/*  public static void main( String args[] ) {

    JFrame appWindow = new JFrame( "Hello Kitty!" );

    appWindow.addWindowListener(
      new WindowAdapter() {
        public void windowClosing( WindowEvent e ) {
          System.exit( 0 );
        }
      }
    );

    HelloKitty game = new HelloKitty();
    game.init();
  }//end MAIN
*/
}//end class HelloKitty
