package Classes;

import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

import Exceptions.DeadPlayer;
import Exceptions.InvalidMove;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class GameData extends JPanel implements ActionListener {

	private MapData map;
	//public Teams teams;
	private Map<String,PlayerUI> allPlayers;

	private Timer timer;
	private int delay=8;
	
	private KeyboardListener keyboardListener;

	private boolean play = true;

	private Image img;
	private final String imagePath = "images/";

	// right side background
	private int mapSize;
	private int blockSize;
	private int length;
	
	public GameData(int mapSize, int blockSize, int[][] playerPositions) {
		this.mapSize = mapSize;
		this.blockSize = blockSize;
		this.length = mapSize * blockSize;

		try {
			img = ImageIO.read(new File(imagePath + "block2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.map = new MapData(mapSize, playerPositions);
		
		// Inicializar equipas e players
		this.allPlayers = new HashMap<>();
		
		// Equipa Vermelha
        for (int i = 0; i < 5; i++) { 
			PlayerUI p = new PlayerUI("R" + i, imagePath + "tank_redteam.png",imagePath + "red_down.png",imagePath + "red_left.png", imagePath + "red_right.png", playerPositions[i][0], playerPositions[i][1], blockSize);
            allPlayers.put(p.getIdPlayer(), p);
        }
		// Equipa Azul
		for (int j = 5; j < playerPositions.length; j++) {
			PlayerUI p = new PlayerUI("B" + (j-5), imagePath + "tank_blueteam.png",imagePath + "blue_down.png",imagePath + "blue_left.png", imagePath + "blue_right.png", playerPositions[j][0], playerPositions[j][1], blockSize);
			allPlayers.put(p.getIdPlayer(), p);
		}
		
		keyboardListener = new KeyboardListener(); // teclas
		
		setFocusable(true);
		//addKeyListener(this);
		addKeyListener(keyboardListener);
		setFocusTraversalKeysEnabled(false);
        timer=new Timer(delay,this);
		timer.start();
	}

	public Map<String, List<Position>> getVisionFieldList(int[][] args) {
		return map.getVisionFieldList(args);
	}


	public Map<String, List<Position>> getVisionFieldList() {
		Map<String, Position> playerPos = new HashMap<>();
		
		for(Entry<String,PlayerUI> entry : this.allPlayers.entrySet()) {
			playerPos.put(entry.getKey(), new Position(entry.getValue().getPosX(), entry.getValue().getPosY()));
		}
		return map.getVisionFieldList(playerPos);
	}


	public List<Position> getVisionField(Position p) {
		return map.getVisionField(p);
	}
	
	
	public void paint(Graphics g) {
		// Desenhar o background
		for (int x = 0; x < length; x += blockSize) {
			for (int y = 0; y < length; y += blockSize) {
				g.drawImage(img, x, y, null);
			}
		}

		// play background
		/*g.setColor(Color.black);
		g.fillRect(0, 0, 650, 600);*/
		
		// right side background
		g.setColor(Color.DARK_GRAY);
		g.fillRect(mapSize * blockSize, 0, 140, length); //antes estava 600
		
		// Se existirem player

		if(play) {
			for(PlayerUI p : allPlayers.values()){
				if (p.getAlive())
					p.setImg(this, g);
			}
		}

		drawScores(g);
		g.dispose();
	}

	

	public void drawScores(Graphics g) {
		int textPosX = length + 10;
		// the scores 		
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD, 15));

		// Pontuação
		//g.drawString("Scores", textPosX ,30);
		//g.drawString("Team Blue:  "+ playerUI1.getScore(), textPosX,60);
		

		// Número players vivos de cada equipa
		g.drawString("Lives", textPosX,30);
		g.drawString("Team Blue:  " + getTeamLives("B") , textPosX,60);

		g.drawString("Lives", textPosX,150);
		g.drawString("Team Red:  " + getTeamLives("R") , textPosX,180);
		
		if(getTeamLives("R") == 0 || getTeamLives("B") == 0) {
			g.setColor(Color.white);
			g.setFont(new Font("serif",Font.BOLD, 60));
			g.drawString("Game Over", 200,300);
			g.drawString("Player 2 Won", 180,380);
			play = false;
			g.setColor(Color.white);
			g.setFont(new Font("serif",Font.BOLD, 30));
			g.drawString("(Space to Restart)", 230,430);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();

		repaint();
	}

	public int getTeamLives(String team) {
		int lives = 0;
		for(PlayerUI p : allPlayers.values()) {
			if (p.getIdTeam().equals(team) && p.getAlive())
				lives++;
		}
		return lives;
	}

	// Lista de ids dos jogadores assassinados
	// quim formação dramaatiiica
	//eu já obtive essa informação
	public List<String> checkDeadPlayers(Position p) {
		List<String> deadPlayers = new ArrayList<>();
		deadPlayers = map.checkDeadPlayers(p);

		for (String string : deadPlayers) {
			
			// Eliminar player do mapa
			allPlayers.remove(string);
			
		}

		return deadPlayers;
	}


	public Position movePlayer(Position pOld,String idPlayer, int direction) throws InvalidMove, DeadPlayer { // add Player
		PlayerUI p = allPlayers.get(idPlayer);
		if (p == null) throw new DeadPlayer();
		Position newPos = map.registerMove(allPlayers.get(idPlayer).getIdTeam(), pOld, direction);
		allPlayers.get(idPlayer).move(newPos, direction);
		repaint();
		return newPos;
	}

	private class KeyboardListener implements KeyListener {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}		
		public void keyPressed(KeyEvent e) {	
			if(e.getKeyCode()== KeyEvent.VK_SPACE && (getTeamLives("R") == 0 || getTeamLives("B") == 0)) {
				play = true;
				repaint();
			}

		}
	}
		
}