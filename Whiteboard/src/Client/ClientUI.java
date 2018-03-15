/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Common.DrawingBoard;
import Common.Constants;
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
import Common.INotificationListener;
import java.awt.Color;
import java.rmi.ConnectException;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
/**
 *
 * @author biruk
 */
public class ClientUI extends JFrame implements INotificationListener {
    private javax.swing.JPanel CanvasPanel;
    private javax.swing.JPanel ColorsPanel;
    private javax.swing.JPanel DrawingOptionsPanel;
    private javax.swing.JPanel MessagePanel;
    private javax.swing.JPanel ShapesPanel;
    private javax.swing.JPanel ShapeOptionsPanel;
    
    private javax.swing.JRadioButton LineRadioButton;
    private javax.swing.JRadioButton OvalRadioButton;
    private javax.swing.JRadioButton RectangleRadioButton;
    private javax.swing.JRadioButton PenRadioButton;
    private javax.swing.JRadioButton EraserRadioButton;
    
    private javax.swing.JCheckBox isFilledCheckbox;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar toolBar;
    
    private javax.swing.JTextArea MessageList;
    private javax.swing.JTextField MessageTextArea;
    
    private javax.swing.JButton AdvancedButton;
    private javax.swing.JButton SendButton;
    private javax.swing.JButton UndoButton;
    private javax.swing.JButton DisconnectButton;
    
    private javax.swing.JList<String> ColorsList;
    private javax.swing.ButtonGroup ShapesRadioGroup;
    
    private DrawingBoard drawingBoard;
    //private DefaultListModel listModel;
    
    private String username;
    private String hostname;
    private IHandler handler;
    
    public ClientUI(String username, String hostname) throws RemoteException, NotBoundException, MalformedURLException {
        this.username = username;
        this.hostname = hostname;
        
        initComponents();        
        setTitle(username);
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
            Logger.getLogger(ClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    
    private void initComponents() throws RemoteException, NotBoundException, MalformedURLException {
        toolBar = new javax.swing.JToolBar();
        ShapesRadioGroup = new javax.swing.ButtonGroup();
        ColorsList = new javax.swing.JList<>();
        
        DrawingOptionsPanel = new javax.swing.JPanel();
        ShapesPanel = new javax.swing.JPanel();
        ColorsPanel = new javax.swing.JPanel();
        CanvasPanel = new javax.swing.JPanel();
        MessagePanel = new javax.swing.JPanel();
        ShapeOptionsPanel = new javax.swing.JPanel();
        
        AdvancedButton = new javax.swing.JButton();
        SendButton = new javax.swing.JButton();
        UndoButton = new javax.swing.JButton();
        DisconnectButton = new javax.swing.JButton();
        
       // jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        
        MessageList = new javax.swing.JTextArea();
        MessageTextArea = new javax.swing.JTextField();
        
        isFilledCheckbox = new javax.swing.JCheckBox();
        
        PenRadioButton = new javax.swing.JRadioButton();
        LineRadioButton = new javax.swing.JRadioButton();
        RectangleRadioButton = new javax.swing.JRadioButton();
        OvalRadioButton = new javax.swing.JRadioButton();
        EraserRadioButton = new javax.swing.JRadioButton();
        
        drawingBoard = new DrawingBoard(this, username, hostname);
        drawingBoard.addNotificationListener(this);
        handler = drawingBoard.getHandler();
        loadMessages();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Shared Whiteboard");
        setResizable(false);
        
        toolBar.setRollover(true);
        
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
        toolBar.add(UndoButton);
        
        DisconnectButton.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        DisconnectButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Close24px.png"))); // NOI18N
        DisconnectButton.setText("Disconnect");
        DisconnectButton.setFocusable(false);
        DisconnectButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        DisconnectButton.setMaximumSize(new java.awt.Dimension(100, 60));
        DisconnectButton.setMinimumSize(new java.awt.Dimension(100, 60));
        DisconnectButton.setPreferredSize(new java.awt.Dimension(100, 60));
        DisconnectButton.setVerifyInputWhenFocusTarget(false);
        DisconnectButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(DisconnectButton);
       
        DrawingOptionsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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
                .addComponent(PenRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EraserRadioButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ColorsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Color", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        //jScrollPane1.setViewportView(ColorsList);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    JOptionPane.showMessageDialog(null, "Connection Failed", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
                catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(ClientUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        SendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handler.sendMessage(handler.getClient(), MessageTextArea.getText());
                } 
                catch (ConnectException ex) {
                    JOptionPane.showMessageDialog(null, "Connection Failed", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
                catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(ClientUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        DisconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handler.disconnect();
                }
                catch (ConnectException ex) {
                    JOptionPane.showMessageDialog(null, "Connection Failed", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
                catch (RemoteException ex) {
                    Logger.getLogger(ClientUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                finally {
                    System.exit(0);
                }
            }
        });
        
        AdvancedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(ClientUI.this, "Choose Color", Color.WHITE);
                if (color != null) {
                    drawingBoard.setShapeColor(color);
                }
            }
        });
        
        pack();
    }  

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new ClientUI("Biruk", "localhost").setVisible(true);
                } catch (RemoteException ex) {
                    Logger.getLogger(ClientUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NotBoundException ex) {
                    Logger.getLogger(ClientUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ClientUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @Override
    public void syncMessageBoard() {
        loadMessages();
    }

    @Override
    public void kickedOut() {
        dispose();
    }

    @Override
    public void serverTerminated() {
        //JOptionPane.showMessageDialog(null, Constants.Server_Terminated_Message, "Server Terminated", JOptionPane.WARNING_MESSAGE);
        System.exit(0);
    }
    
    private class ShapeSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JRadioButton selected = (JRadioButton) e.getSource();
            drawingBoard.setShapeType(selected.getText());
        }
    }
}