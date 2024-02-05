package edu.uwm.cs751;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
// Took from https://www3.ntu.edu.sg/home/ehchua/programming/java/j4a_gui.html#zz-9.2


public class SimpleGUI extends TrieBasedSimilaritySearch {

	private TrieBasedSimilaritySearch trie; 
	private Path currentPath;
	public SimpleGUI() {

		trie = new TrieBasedSimilaritySearch();
		createFileUploaderWindow();

	}

	private void createFileUploaderWindow() {


		JFrame frame1 = new JFrame("Trie Based Similarity Search");  


		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(500, 200);

		JButton fileChooserButton = new JButton("Upload a Text File to Start");
		fileChooserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
				fileChooser.setFileFilter(filter);

				int returnValue = fileChooser.showOpenDialog(frame1);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						Path filePath = fileChooser.getSelectedFile().toPath();
						currentPath = filePath;
						if (insertAllFromFile(currentPath, trie)) {
							JOptionPane.showMessageDialog(frame1,getColoredLabel ("Words from the file are inserted into the Trie.",2));
							frame1.dispose(); // Close file uploader window
							createOperationsWindow(); // Open Trie operations window
						} else {
							JOptionPane.showMessageDialog(frame1,getColoredLabel ("Failed to insert words from the file.",0));
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(frame1,  getColoredLabel(("Error occurred: " + ex.getMessage()),0));
					}
				}
			}
		});

		frame1.setLayout(new FlowLayout());
		frame1.add(fileChooserButton);
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
	}
	private void createOperationsWindow() {
		JFrame frame = new JFrame("Trie Operations");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 200);


		// Create buttons
		JButton insertButton = new JButton("Insert");
		JButton searchButton = new JButton("Search");
		JButton searchSimilar = new JButton("Similarity Search");
		JButton exitButton = new JButton("Exit");
		JButton credits = new JButton("Credits");

		// Add action listeners to buttons
		insertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String wordToInsert = JOptionPane.showInputDialog(frame, "Enter a word to insert(only lowercase)");

				if (wordToInsert == null) {
					return; 
				}
				try {  if (!wordToInsert.isEmpty()&&wordToInsert.equals(wordToInsert.toLowerCase())&&!trie.search(wordToInsert)) {
					trie.insert(wordToInsert); 
					if (currentPath != null) {
						writeWordToFile(currentPath, wordToInsert); 
						JOptionPane.showMessageDialog(frame, "The word \"" + wordToInsert + "\" has been inserted into the file and Trie.");
					} else {
						JOptionPane.showMessageDialog(frame, "No file is currently loaded.");
					}

				}
				else if(!wordToInsert.equals(wordToInsert.toLowerCase())){
					JOptionPane.showMessageDialog(frame, getColoredLabel("Only lowercase is allowed",0) );
				}else if(wordToInsert.isEmpty()){
					JOptionPane.showMessageDialog(frame, getColoredLabel("Word is empty",0) );
				}
				else if(trie.search(wordToInsert))
				{
					JOptionPane.showMessageDialog(frame, "Word is already found in the Trie file" );
				}
				}  catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(frame, getColoredLabel(ex.getMessage(),0));
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, getColoredLabel(("Error occurred: " + ex.getMessage()),0));
				}

			}
		});



		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String wordToSearch = JOptionPane.showInputDialog(frame, "Enter a word to search:");
				try {     if (wordToSearch != null && !wordToSearch.isEmpty()) {

					boolean found = trie.search(wordToSearch);
					if (found) {
						JOptionPane.showMessageDialog(frame, "The word \"" + wordToSearch + "\" exists in the Trie.");
					} else {
						JOptionPane.showMessageDialog(frame, "The word \"" + wordToSearch + "\" does not exist in the Trie.");
						int insertOption = JOptionPane.showConfirmDialog(frame, "Do you want to insert \"" + wordToSearch + "\" into the Trie?", "Insert Word", JOptionPane.YES_NO_OPTION);
						if (insertOption == JOptionPane.YES_OPTION) {
							trie.insert(wordToSearch); // Insert word into the Trie
							if (currentPath != null) {
								writeWordToFile(currentPath, wordToSearch); // Write word to the file
								JOptionPane.showMessageDialog(frame, "The word \"" + wordToSearch + "\" has been inserted into the file and Trie.");
							}
						}
					}
				} else {
					JOptionPane.showMessageDialog(frame, "No word entered or cancelled.");
				}
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, getColoredLabel(("Error occurred: " + ex.getMessage()),0));
				}
			}
		});


		searchSimilar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String wordToSearch = JOptionPane.showInputDialog(frame, "Enter a word to search similarity:");

				if (wordToSearch == null) {
					return; 
				}
				try {     if ( !wordToSearch.isEmpty()&&wordToSearch.equals(wordToSearch.toLowerCase())) {
					
						String thresholdStr = JOptionPane.showInputDialog(frame, "Enter the edit distance threshold (Max. "+wordToSearch.length()+")");
						try {
							int threshold = Integer.parseInt(thresholdStr);
							Set<String> similarWords;
							if(threshold>=wordToSearch.length()&&threshold!=0)

							{    



								threshold = wordToSearch.length()-1;
								similarWords = trie.findSimilarWords(wordToSearch, wordToSearch.length());
								JOptionPane.showMessageDialog(frame, "The maximum thrushold allowed is less than "+ wordToSearch.length()+ ",so setting to(Max)= "+threshold);



							}

							else
								similarWords = trie.findSimilarWords(wordToSearch, threshold);

							if (similarWords.isEmpty()||threshold==0) {



								if(threshold==0)
									JOptionPane.showMessageDialog(frame, "Since thrushold is 0 the word found is "+wordToSearch);
								else 
									JOptionPane.showMessageDialog(frame, "No similar words for \"" + wordToSearch + "\" within edit distance of " + threshold);
								
							} else {

								List<String> sortedSimilarWords = new ArrayList<>(similarWords);
								Collections.sort(sortedSimilarWords);
								StringBuilder similarWordsStr = new StringBuilder();
								for (String word : sortedSimilarWords) {
									similarWordsStr.append(word).append("\n");
								}
								JLabel label = new JLabel("Found " + similarWords.size()+" similar words for \""+wordToSearch+ "\" within edit distance of " + threshold );
								JTextArea textArea = new JTextArea(10, 30); 
								textArea.setText(similarWordsStr.toString());
								textArea.setEditable(false); 
								textArea.setLineWrap(true);
								textArea.setWrapStyleWord(true);

								JScrollPane scrollPane = new JScrollPane(textArea);
								scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
								scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

								// Create a panel to hold both label and scroll pane
								JPanel panel = new JPanel();
								panel.setLayout(new BorderLayout());
								panel.add(label, BorderLayout.NORTH);
								panel.add(scrollPane, BorderLayout.CENTER);

								JOptionPane.showMessageDialog(frame, panel, "Similar Words", JOptionPane.INFORMATION_MESSAGE);
							
							}
						} catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(frame, getColoredLabel("Please enter a valid number for the threshold.",0));
						}
						JOptionPane.showMessageDialog(frame, getColoredLabel("Search completed for \"" + wordToSearch + "\".",2));
					
				}

				else if(!wordToSearch.equals(wordToSearch.toLowerCase())){
					JOptionPane.showMessageDialog(frame,getColoredLabel( "Only lowercase is allowed" ,0));
				}else if(wordToSearch.isEmpty()){
					JOptionPane.showMessageDialog(frame, getColoredLabel("Word is empty",2) );
				} }
				catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(frame, getColoredLabel(ex.getMessage(),0));
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, getColoredLabel("Error occurred: " + ex.getMessage(),0));
				}


			}});
		credits.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				StringBuilder namesBuilder = new StringBuilder();
		        namesBuilder.append("CS751 Project at UWM\nUnder Dr.John Boyland\n\nCredits:\n");

				for (String name : names) {
					namesBuilder.append(name).append("\n");
				}

				JOptionPane.showMessageDialog(frame, namesBuilder.toString());

			}
		});
		exitButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		// Layout the buttons in the frame
		frame.setLayout(new FlowLayout());
		frame.add(insertButton);
		frame.add(searchButton);

		frame.add(searchSimilar);

		frame.add(credits);
		frame.add(exitButton);
		// Display the window
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		frame.setLayout(new FlowLayout());
		frame.setVisible(true);
	}

	private String[] names = {"Aaron M", "Sheethal S", "Sowmiya S","Sreeteja T","Xavier S"};
	//taken from https://www.colorcombos.com/colors
	private static final String[] COLORS = {
			"#FF0000", // Red 
			"#00FF00", // Green
			"#0000FF", // Blue
			"#FFFF00", // Yellow
			"#FF00FF", // Magenta
			"#00FFFF"  // Cyan
	};

	public static JLabel getColoredLabel(String text,int num) {
		Random rand = new Random();

		int  randomColorIndex;
		if(num>5)
			randomColorIndex = rand.nextInt(COLORS.length);
		else 
			randomColorIndex=num;

		JLabel label = new JLabel(text);
		label.setForeground(Color.decode(COLORS[randomColorIndex]));
		return label;
	}

	private static boolean insertAllFromFile(Path filePath, TrieBasedSimilaritySearch trie) {
		if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
			System.out.println(filePath.getFileName() + " is invalide file.");
			return false;
		}
		System.out.println("Inserting all the words from " + filePath.getFileName());
		try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
			String word;
			while ((word = br.readLine()) != null) {
				trie.insert(word);
			}
		} catch (IOException e) {
			System.err.println("Not able to read the file: " + e.getMessage());
		}
		return true;
	}

	/**
	 * Add word to file
	 * 
	 * @param filePath In which word need to be added.
	 * @param word     that need to be added.
	 */
	private static void writeWordToFile(Path filePath, String word) {
		try (FileWriter fw = new FileWriter(filePath.toFile(), true)) {
			fw.write(word);
			fw.write(System.lineSeparator());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SimpleGUI(); 
			}
		});
	}
}
