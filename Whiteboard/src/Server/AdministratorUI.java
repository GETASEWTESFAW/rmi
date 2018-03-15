/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;


import Common.Constants;
import Common.DrawingBoard;
import Common.IClient;
import Common.IClientsListChangeListener;
import Common.IHandler;
import Common.Message;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JOptionPane;
import Common.INotificationListener;
import Common.IServerTerminationReadyListener;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.ConnectException;
import java.rmi.UnmarshalException;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
/**
 *
 * @author biruk
 */
public class AdministratorUI extends JFrame implements INotificationListener, IClientsListChangeListener, IServerTerminationReadyListener {
    private javax.swing.JPanel CanvasPanel;
    private javax.swing.JPanel ColorsPanel;
    private javax.swing.JPanel DrawingOptionsPanel;
    private javax.swing.JPanel MessagePanel;
    private javax.swing.JPanel ShapeOptionsPanel;
    private javax.swing.JPanel ShapesPanel;
    private javax.swing.JPanel ClientsPanel;
      
    private javax.swing.JComboBox<String> ClientsComboBox;
    
    
    private javax.swing.ButtonGroup ShapesRadioGroup;
    private javax.swing.JRadioButton OvalRadioButton;
    private javax.swing.JRadioButton PenRadioButton;
    private javax.swing.JRadioButton RectangleRadioButton;  
    private javax.swing.JRadioButton LineRadioButton;
    private javax.swing.JRadioButton EraserRadioButton;
    
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea MessageList;
    private javax.swing.JTextField MessageTextArea;
    
    private javax.swing.JButton AdvancedButton;
    private javax.swing.JButton UndoButton;
    private javax.swing.JButton SendButton;
    private javax.swing.JButton KickOutButton;
    private javax.swing.JButton NewButton;
    private javax.swing.JButton OpenButton;
    private javax.swing.JButton SaveButton;
    private javax.swing.JButton CloseButton;
    
    private javax.swing.JMenuItem NewFileMenuItem;
    private javax.swing.JMenuItem OpenFileMenuItem;
    private javax.swing.JMenuItem SaveFileMenuItem;
    private javax.swing.JMenuItem CloseFileMenuItem;
    
    private javax.swing.JCheckBox isFilledCheckbox;
    private javax.swing.JToolBar toolbar;
    private javax.swing.JMenu FileMenu;
    private javax.swing.JMenuBar MenuBar;
    
    private DrawingBoard drawingBoard;
    //private DefaultListModel listModel;
    
    private String hostname;
    private String username;
    private IHandler handler;
    
    public AdministratorUI(String username, String hostname) throws RemoteException, NotBoundException, MalformedURLException {
        this.username = username;
        this.hostname = hostname;
            
        initComponents();
        loadUsers();
    }
    
    public void loadUsers() throws RemoteException {
        LinkedList<IClient> clients = handler.getServer().getClients();
        ClientsComboBox.removeAllItems();
        if (clients.size() == 0) {
            ClientsComboBox.addItem(Constants.No_Clients_Message);
        }
        else {
            for (IClient client : clients) {
                ClientsComboBox.addItem(client.getName());
            }
        }
    }
    
    public void loadMessages() {
        LinkedList<Message> messages;
        StringBuilder messagesStringBuilder = new StringBuilder();
        try {
            messages = this.handler.getMessages();
            for (Message message : messages) {
                messagesStringBuilder.append(message.getMessage());
                
            }
            MessageList.setText(messagesStringBuilder.toString());
        } 
        catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }   
    }
    

    

    
    public void newBoard() throws RemoteException {
        
        handler.getServer().newBoard();
    }
    
    public void saveBoard() throws RemoteException, FileNotFoundException, IOException {
        JFileChooser chooser = new JFileChooser();
        int returnValue = chooser.showSaveDialog(AdministratorUI.this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            handler.getServer().saveBoard(outputStream);
        }
    }
    
    public void openBoard() throws FileNotFoundException, IOException {
        JFileChooser chooser = new JFileChooser();
        int returnValue = chooser.showOpenDialog(AdministratorUI.this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
            handler.getServer().openBoard(inputStream);
        }
    }
    
    public void closeBoard() throws RemoteException {
        try {
            handler.getServer().close();
        }
        catch (UnmarshalException ex) {
        }
        finally {
            dispose();
            System.exit(0);
        }
    }
    
    public void initComponents() throws RemoteException, NotBoundException, MalformedURLException {
        toolbar = new javax.swing.JToolBar();
        
        ShapesPanel = new javax.swing.JPanel();
        MessagePanel = new javax.swing.JPanel();
        DrawingOptionsPanel = new javax.swing.JPanel();
        ColorsPanel = new javax.swing.JPanel();
        CanvasPanel = new javax.swing.JPanel();
        ShapeOptionsPanel = new javax.swing.JPanel();
        ClientsPanel = new javax.swing.JPanel();
        
        UndoButton = new javax.swing.JButton();
        AdvancedButton = new javax.swing.JButton();
        SendButton = new javax.swing.JButton();
        KickOutButton = new javax.swing.JButton();
        NewButton = new javax.swing.JButton();
        OpenButton = new javax.swing.JButton();
        SaveButton = new javax.swing.JButton();
        CloseButton = new javax.swing.JButton();
        
        ShapesRadioGroup = new javax.swing.ButtonGroup();
        EraserRadioButton = new javax.swing.JRadioButton();
        LineRadioButton = new javax.swing.JRadioButton();
        RectangleRadioButton = new javax.swing.JRadioButton();
        OvalRadioButton = new javax.swing.JRadioButton();
        PenRadioButton = new javax.swing.JRadioButton();

        jScrollPane2 = new javax.swing.JScrollPane();
        
        ClientsComboBox = new javax.swing.JComboBox<>();
        
 
        isFilledCheckbox = new javax.swing.JCheckBox();
        
        MessageList = new javax.swing.JTextArea();
        MessageTextArea = new javax.swing.JTextField();
        
        MenuBar = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        NewFileMenuItem = new javax.swing.JMenuItem();
        OpenFileMenuItem = new javax.swing.JMenuItem();
        SaveFileMenuItem = new javax.swing.JMenuItem();
        CloseFileMenuItem = new javax.swing.JMenuItem();
        
        drawingBoard = new DrawingBoard(this, username, hostname);
        handler = drawingBoard.getHandler();
        drawingBoard.addNotificationListener(this);
        handler.getServer().addClientListChangeListener(this);
        
        FileMenu.setText("File");

        NewFileMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        NewFileMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/New16px.png"))); // NOI18N
        NewFileMenuItem.setText("New");
        FileMenu.add(NewFileMenuItem);

        OpenFileMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        OpenFileMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Open16px.png"))); // NOI18N
        OpenFileMenuItem.setText("Open");
        FileMenu.add(OpenFileMenuItem);

        SaveFileMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        SaveFileMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Save16px.png"))); // NOI18N
        SaveFileMenuItem.setText("Save");
        FileMenu.add(SaveFileMenuItem);

        CloseFileMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        CloseFileMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Close16px.png"))); // NOI18N
        CloseFileMenuItem.setText("Close");
        FileMenu.add(CloseFileMenuItem);

        MenuBar.add(FileMenu);

        setJMenuBar(MenuBar);
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Shared Whiteboard");
        setResizable(false);

        toolbar.setRollover(true);
        
        NewButton.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        NewButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/New24px.png"))); // NOI18N
        NewButton.setText("New");
        NewButton.setFocusable(false);
        NewButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        NewButton.setMaximumSize(new java.awt.Dimension(60, 60));
        NewButton.setMinimumSize(new java.awt.Dimension(60, 60));
        NewButton.setPreferredSize(new java.awt.Dimension(60, 60));
        NewButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbar.add(NewButton);

        OpenButton.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        OpenButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Open24px.png"))); // NOI18N
        OpenButton.setText("Open");
        OpenButton.setFocusable(false);
        OpenButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        OpenButton.setMaximumSize(new java.awt.Dimension(60, 60));
        OpenButton.setMinimumSize(new java.awt.Dimension(60, 60));
        OpenButton.setPreferredSize(new java.awt.Dimension(60, 60));
        OpenButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbar.add(OpenButton);

        SaveButton.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        SaveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Save24px.png"))); // NOI18N
        SaveButton.setText("Save");
        SaveButton.setFocusable(false);
        SaveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SaveButton.setMaximumSize(new java.awt.Dimension(60, 60));
        SaveButton.setMinimumSize(new java.awt.Dimension(60, 60));
        SaveButton.setPreferredSize(new java.awt.Dimension(60, 60));
        SaveButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbar.add(SaveButton);

        CloseButton.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        CloseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Close24px.png"))); // NOI18N
        CloseButton.setText("Close");
        CloseButton.setFocusable(false);
        CloseButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        CloseButton.setMaximumSize(new java.awt.Dimension(60, 60));
        CloseButton.setMinimumSize(new java.awt.Dimension(60, 60));
        CloseButton.setPreferredSize(new java.awt.Dimension(60, 60));
        CloseButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbar.add(CloseButton);
        
        UndoButton.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        UndoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Undo-icon.png"))); // NOI18N
        UndoButton.setText("Undo");
        UndoButton.setFocusable(false);
        UndoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        UndoButton.setMaximumSize(new java.awt.Dimension(60, 60));
        UndoButton.setMinimumSize(new java.awt.Dimension(60, 60));
        UndoButton.setPreferredSize(new java.awt.Dimension(60, 60));
        UndoButton.setVerifyInputWhenFocusTarget(false);
        UndoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbar.add(UndoButton);
        
        ClientsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Clients", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N
        ClientsPanel.setMaximumSize(new java.awt.Dimension(350, 100));
        ClientsPanel.setMinimumSize(new java.awt.Dimension(350, 100));
        ClientsPanel.setPreferredSize(new java.awt.Dimension(350, 100));
        ClientsPanel.setRequestFocusEnabled(false);

        KickOutButton.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        KickOutButton.setText("Kick Out");

        javax.swing.GroupLayout ClientsPanelLayout = new javax.swing.GroupLayout(ClientsPanel);
        ClientsPanel.setLayout(ClientsPanelLayout);
        ClientsPanelLayout.setHorizontalGroup(
            ClientsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ClientsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ClientsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(KickOutButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        ClientsPanelLayout.setVerticalGroup(
            ClientsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ClientsPanelLayout.createSequentialGroup()
                .addGroup(ClientsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ClientsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(KickOutButton))
                .addGap(0, 46, Short.MAX_VALUE))
        );

        toolbar.add(ClientsPanel);

        ShapesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Shapes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        ShapesRadioGroup.add(LineRadioButton);
        LineRadioButton.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        LineRadioButton.setText("Line");
        LineRadioButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Line.png"))); // NOI18N

        ShapesRadioGroup.add(RectangleRadioButton);
        RectangleRadioButton.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        RectangleRadioButton.setText("Rectangle");
        RectangleRadioButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Rectangle.png"))); // NOI18N

        ShapesRadioGroup.add(OvalRadioButton);
        OvalRadioButton.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        OvalRadioButton.setText("Oval");
        OvalRadioButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Ellipse.png"))); // NOI18N


        ShapesRadioGroup.add(PenRadioButton);
        PenRadioButton.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        PenRadioButton.setText("Pen");
        PenRadioButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Pen.png"))); // NOI18N
        
        ShapesRadioGroup.add(EraserRadioButton);
        EraserRadioButton.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        EraserRadioButton.setText("Eraser");
        EraserRadioButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Eraser.png"))); // NOI18N
        
        javax.swing.GroupLayout ShapesPanelLayout = new javax.swing.GroupLayout(ShapesPanel);
        ShapesPanel.setLayout(ShapesPanelLayout);
        ShapesPanelLayout.setHorizontalGroup(
            ShapesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShapesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ShapesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LineRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(RectangleRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                    .addComponent(OvalRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PenRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(EraserRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        ShapesPanelLayout.setVerticalGroup(
            ShapesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShapesPanelLayout.createSequentialGroup()
                .addComponent(LineRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RectangleRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OvalRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PenRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EraserRadioButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ColorsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Color", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N


        AdvancedButton.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        AdvancedButton.setText("Advanced");

        javax.swing.GroupLayout ColorsPanelLayout = new javax.swing.GroupLayout(ColorsPanel);
        ColorsPanel.setLayout(ColorsPanelLayout);
        ColorsPanelLayout.setHorizontalGroup(
            ColorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ColorsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ColorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AdvancedButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        ColorsPanelLayout.setVerticalGroup(
            ColorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ColorsPanelLayout.createSequentialGroup()
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AdvancedButton))
        );

        
        ShapeOptionsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Shape Options", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        isFilledCheckbox.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        isFilledCheckbox.setText("Filled");

        javax.swing.GroupLayout ShapeOptionsPanelLayout = new javax.swing.GroupLayout(ShapeOptionsPanel);
        ShapeOptionsPanel.setLayout(ShapeOptionsPanelLayout);
        ShapeOptionsPanelLayout.setHorizontalGroup(
            ShapeOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShapeOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(isFilledCheckbox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        ShapeOptionsPanelLayout.setVerticalGroup(
            ShapeOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShapeOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(isFilledCheckbox)
                .addContainerGap())
        );

        javax.swing.GroupLayout DrawingOptionsPanelLayout = new javax.swing.GroupLayout(DrawingOptionsPanel);
        DrawingOptionsPanel.setLayout(DrawingOptionsPanelLayout);
        DrawingOptionsPanelLayout.setHorizontalGroup(
            DrawingOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DrawingOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DrawingOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ColorsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ShapesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ShapeOptionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        DrawingOptionsPanelLayout.setVerticalGroup(
            DrawingOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DrawingOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ShapesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ShapeOptionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ColorsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );


        CanvasPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Drawing Board", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        drawingBoard.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout DrawingBoardLayout = new javax.swing.GroupLayout(drawingBoard);
        drawingBoard.setLayout(DrawingBoardLayout);
        DrawingBoardLayout.setHorizontalGroup(
            DrawingBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 631, Short.MAX_VALUE)
        );
        DrawingBoardLayout.setVerticalGroup(
            DrawingBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout CanvasPanelLayout = new javax.swing.GroupLayout(CanvasPanel);
        CanvasPanel.setLayout(CanvasPanelLayout);
        CanvasPanelLayout.setHorizontalGroup(
            CanvasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CanvasPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(drawingBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        CanvasPanelLayout.setVerticalGroup(
            CanvasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CanvasPanelLayout.createSequentialGroup()
                .addComponent(drawingBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        MessagePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chat", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        MessageList.setColumns(20);
        MessageList.setRows(5);
        jScrollPane2.setViewportView(MessageList);

        SendButton.setText("Send");

        javax.swing.GroupLayout MessagePanelLayout = new javax.swing.GroupLayout(MessagePanel);
        MessagePanel.setLayout(MessagePanelLayout);
        MessagePanelLayout.setHorizontalGroup(
            MessagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MessagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MessagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                    .addGroup(MessagePanelLayout.createSequentialGroup()
                        .addComponent(MessageTextArea)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SendButton)))
                .addContainerGap())
        );
        MessagePanelLayout.setVerticalGroup(
            MessagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MessagePanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MessagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SendButton)
                    .addGroup(MessagePanelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(MessageTextArea)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(DrawingOptionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CanvasPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MessagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DrawingOptionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MessagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CanvasPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        
        ShapeSelectionListener shapeListener = new ShapeSelectionListener();
        LineRadioButton.addActionListener(shapeListener);
        RectangleRadioButton.addActionListener(shapeListener);
        OvalRadioButton.addActionListener(shapeListener);
        PenRadioButton.addActionListener(shapeListener);
        EraserRadioButton.addActionListener(shapeListener);
        
        MenuListener menuListener = new MenuListener();
        NewFileMenuItem.addActionListener(menuListener);
        OpenFileMenuItem.addActionListener(menuListener);
        SaveFileMenuItem.addActionListener(menuListener);
        CloseFileMenuItem.addActionListener(menuListener);
        
        ToolbarButtonsListener toolbarButtonsListener = new ToolbarButtonsListener();
        NewButton.addActionListener(toolbarButtonsListener);
        OpenButton.addActionListener(toolbarButtonsListener);
        SaveButton.addActionListener(toolbarButtonsListener);
        CloseButton.addActionListener(toolbarButtonsListener);
        
        
                isFilledCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setShapeFilled(isFilledCheckbox.isSelected());
            }
        });
        
        UndoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handler.undo();
                } 
                catch (ConnectException ex) {
                    JOptionPane.showMessageDialog(null, "Client Has Disconnected", "Error", JOptionPane.ERROR_MESSAGE);
                } 
                catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(AdministratorUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        SendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handler.sendMessage(handler.getClient(), MessageTextArea.getText());
                } 
                catch (RemoteException ex) {
                    
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        KickOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String username = (String)ClientsComboBox.getSelectedItem();
                    
                    if (username != Constants.No_Clients_Message) {
                        handler.getServer().kickoutClient(username);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "No user Selected!", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } 
                catch (ConnectException ex) {
                    try {
                        handler.getServer().validateClientsList();
                    } catch (RemoteException ex1) {
                        Logger.getLogger(AdministratorUI.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    JOptionPane.showMessageDialog(null, "Client Has Disconnected", "Error", JOptionPane.ERROR_MESSAGE);
                } 
                catch (RemoteException ex) {
                    
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(AdministratorUI.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    try {
                        loadUsers();
                    } catch (RemoteException ex1) {
                        Logger.getLogger(AdministratorUI.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
                catch (Exception ex) {
                    Logger.getLogger(AdministratorUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        AdvancedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(AdministratorUI.this, "Choose Color", Color.WHITE);
                if (color != null) {
                    drawingBoard.setShapeColor(color);
                }
            }
        });
        
        pack();
    }

    @Override
    public void syncMessageBoard() {
        loadMessages();
    }

    @Override
    public void clientsListChanged() {
        try {
            loadUsers();
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(AdministratorUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        @Override
    public void kickedOut() {}

    @Override
    public void serverTerminated() {}

    @Override
    public void terminate() {
        dispose();
    }

    
    
    private class ShapeSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JRadioButton selected = (JRadioButton) e.getSource();
            drawingBoard.setShapeType(selected.getText());
        }
    }
    
    public class MenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem item = (JMenuItem) e.getSource();
            if (item.getText().equals("New")) {
                try {
                    newBoard();
                } catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(AdministratorUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (item.getText().equals("Open")) {
                try {
                    openBoard();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(AdministratorUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (item.getText().equals("Save")) {
                try {
                    saveBoard();
                } catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(AdministratorUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(AdministratorUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (item.getText().equals("Close")) {
                try {
                    closeBoard();
                } catch (RemoteException ex) {
                    Logger.getLogger(AdministratorUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public class ToolbarButtonsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton item = (JButton) e.getSource();
            if (item.getText().equals("New")) {
                try {
                    newBoard();
                } catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(AdministratorUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (item.getText().equals("Open")) {
                try {
                    openBoard();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(AdministratorUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (item.getText().equals("Save")) {
                try {
                    saveBoard();
                } catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(AdministratorUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(AdministratorUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (item.getText().equals("Close")) {
                try {
                    closeBoard();
                } catch (RemoteException ex) {
                    Logger.getLogger(AdministratorUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new AdministratorUI("Administrator", "rmi://localhost/Whiteboard").setVisible(true);
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(AdministratorUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NotBoundException ex) {
                    Logger.getLogger(AdministratorUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(AdministratorUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
