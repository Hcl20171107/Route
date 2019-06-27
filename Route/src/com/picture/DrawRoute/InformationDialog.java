package com.picture.DrawRoute;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.JButton; // ��ť
import javax.swing.JComboBox; // �����б����
import javax.swing.JDialog; // �Ի��� û����󻯡���С����ֻ���˳���
import javax.swing.JLabel; // JLabel ���������ʾ�ı���ͼ���ͬʱ��ʾ����
import javax.swing.JPanel; // JPanel �ǶԴ����о�����ͬ�߼����ܵ�����������
import javax.swing.JTextField; // JTextField  ��һ�������������������༭�����ı�
import javax.swing.UIManager;

public abstract class InformationDialog extends JDialog implements ActionListener, 
KeyListener, ItemListener, FocusListener, MouseListener{

	private static final long serialVersionUID = 1176732739988478488L;
	public static final int SCALE = 70; //������һ����λԼΪ75������
	private JPanel centerPanel; // ��Panel
	private JPanel northPanel; // ��Panel 
	private JPanel buttonPanel; // ��ťPanel
	protected JButton btnClose = null; // ȡ�����o
	private JLabel latitudeLabel; // �ص�γ��������ʾ��
	private JLabel longitudeLabel; // �ص㾭��������ʾ�� 
	private JTextField latitudeTextFiled; // ��ʾγ������
	private JTextField longitudeTextFiled; // ��ʾ��������
	private JLabel scaleLabel; // ��������ʾ��
	private JComboBox scaleComboBox; // ��ʾ������
    private boolean blInited = false; // �Ƿ��Ѿ���ʼ��
	public final static int ID_OK = 1;
	public final static int ID_CANCEL = 2;
    
	public InformationDialog() {
		super();
		initUI();                           // ʵ����
	}
	
	public InformationDialog(Frame owner) {
		super(owner);
		initUI();
	}
	
	public InformationDialog (Frame owner, String title) {
		super(owner, title);
		initUI();
	}
	
	protected JPanel createNorthPanel() {
		if (northPanel != null) {
			return northPanel;
		}
		northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(400, 70));
		northPanel.setLayout(null);
		northPanel.add(getLatitudeLabel(), null); 
		northPanel.add(getLongitudeLabel(), null);
		northPanel.add(getLatitudeTextFiled(), null); 
		northPanel.add(getLongitudeTextFiled(), null);
		northPanel.setBackground(Color.LIGHT_GRAY);
		return northPanel;
	}

	protected JPanel createCenterPanel() {
		if (centerPanel != null) {
			return centerPanel;
		}
		centerPanel = new JPanel(){
            @Override
            public void paint(Graphics g)      // Graphics ��paint�����ṩ����
            {
                super.paint(g);
                //  �����滭һ������
                g.setColor(UIManager.getColor("MessageDialog.linecolor"));
                g.drawLine(0, 0, getWidth(), 0);
            }
        };
		centerPanel.setLayout(null);     // Layout(���ֹ�����) ָ������İڷ�λ��
		return centerPanel;
	}

	protected JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel() {
				@Override
				public void paint(Graphics g) {
					super.paint(g);
					// �����滭һ������
					g.setColor(UIManager.getColor("MessageDialog.linecolor"));
					g.drawLine(0, 0, getWidth(), 0);
				}
			};
			// ��ӱ�����ɫ
			buttonPanel.setBackground(Color.LIGHT_GRAY);
			FlowLayout flowLayout = new FlowLayout(FlowLayout.RIGHT, 8, 8);//�Ҷ��룬ˮƽ��϶8����ֱ��϶8
			buttonPanel.setLayout(flowLayout);
		}
		return buttonPanel;
	}
	
    public JButton getBtnClose(){
        if (btnClose == null){
        	btnClose = new JButton("�ر�");
        	btnClose.setToolTipText("(Alt+" + 'C' + ")");
        	btnClose.setMnemonic('C');		
        	btnClose.setBounds(330, 8, 75, 20);
        	btnClose.addActionListener(this);
        }
        return btnClose;
    }
	
	
	
	private JLabel getLatitudeLabel() {
		if (latitudeLabel == null) {
			latitudeLabel = new JLabel("���ȣ�");
			latitudeLabel.setPreferredSize(new Dimension(40, 20)); // �趨�����ʾ��С
			latitudeLabel.setFont(new Font("dialog", 0, 14));// �߼����壬0ƽ����ʽ��1Ϊ���� ���ֺ�
			latitudeLabel.setBounds(5, 40, 40, 20); // ���� ����ߴ��λ�ã�����x������y�����ڵĿ�ȣ����ڵĸ߶� ��
		}
		return latitudeLabel;
	}
	
	private JLabel getLongitudeLabel() {
		if (longitudeLabel == null) {
			longitudeLabel = new JLabel("���ȣ�");
			longitudeLabel.setPreferredSize(new Dimension(40, 20)); 
			longitudeLabel.setFont(new Font("dialog", 0, 14));
			longitudeLabel.setBounds(35, 40, 40, 20);
		}
		return longitudeLabel;
	}

	private JTextField getLatitudeTextFiled() {
		if(null == latitudeTextFiled){
			latitudeTextFiled = new JTextField();
			latitudeTextFiled.setText(AutoCompleter.getCenter());
			latitudeTextFiled.setPreferredSize(new Dimension(160, 20));
			latitudeTextFiled.setBounds(50, 40, 150, 20);
		}
		return latitudeTextFiled;
	}
	
	private JTextField getLongitudeTextFiled() {
		if(null == longitudeTextFiled){
			longitudeTextFiled = new JTextField();
			longitudeTextFiled.setText(AutoCompleter.getCenter());
			longitudeTextFiled.setPreferredSize(new Dimension(160, 20));
			longitudeTextFiled.setBounds(50, 40, 150, 20);
		}
		return longitudeTextFiled;
	}
	
	public PicturePanel getImagePanel(){
		if(null == imagePanel){
			imagePanel = new PicturePanel(this);
			imagePanel.setBorder(BorderFactory.createLineBorder(UIManager
					.getColor("MessageDialog.linecolor")));
			imagePanel.setBounds(10, 4, 400, 300);
		}
		return imagePanel;
	}
	
	public JLabel getScaleLabel(){
		if (scaleLabel == null) {
			scaleLabel = new JLabel("������:");
			scaleLabel.setPreferredSize(new Dimension(60, 20));
			scaleLabel.setFont(new Font("dialog", 0, 14));
			scaleLabel.setBounds(5, 5, 60, 20);
		}
		return scaleLabel;
	}
	
	public JComboBox getScaleComboBox(){
		if( null == scaleComboBox){
			scaleComboBox = new JComboBox();
			scaleComboBox.setModel(new DefaultComboBoxModel(
					AutoCompleter.zoomInfo.toArray(new String[0])));
			scaleComboBox.setPreferredSize(new Dimension(2*SCALE, 20));
			scaleComboBox.setBounds(5, 5, 2*SCALE, 20);
			scaleComboBox.addItemListener( this);
		}
		return scaleComboBox;
	}
	
	/**
	 * ��ʼ���ࡣ
	 */
	public void initUI() {
		 if (this.isBlInited())
	        {
	            return;
	        }
	        getContentPane().setLayout(new BorderLayout());
	        setSize(420, 440);
			//setResizable(false);
	        if (getNorthPanel() != null)
	        {
	            getContentPane().add(getNorthPanel(), BorderLayout.NORTH);
	        }
	        if (getCenterPanel() != null)
	        {
	            getCenterPanel().setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 8));
	            getContentPane().add(getCenterPanel(), BorderLayout.CENTER);
	            getCenterPanel().setBackground(Color.LIGHT_GRAY);
	        }
	        getContentPane().add(getButtonPanel(), BorderLayout.SOUTH);
	        initButton();
	        Component[] components = getButtonPanel().getComponents();
	        if (components == null || components.length == 0)
	        {
	            return;
	        }
	        for (Component component : components)
	        {
	            setPreferredWidth(component);
	        }
	        blInited = true;
	}
	 
	private void setPreferredWidth(Component component) {
		String strText = "";
		boolean blSetSize = true;
		boolean blSetPreferredSize = true;
		if (component instanceof AbstractButton) {
			strText = ((AbstractButton) component).getText();

			if (component instanceof JRadioButton) {
				blSetPreferredSize = false;
			}
		} else if (component instanceof JLabel) {
			strText = ((JLabel) component).getText();
		} else {
			return;
		}
		if (strText == null || strText.trim().length() == 0) {
			return;
		}
		int iWidth = component.getFontMetrics(component.getFont()).stringWidth(
				strText) + 20;
		if (iWidth <= component.getWidth()) {
			return;
		}
		if (blSetSize) {
			component.setSize(iWidth, component.getSize().height);
		}
		if (blSetPreferredSize) {
			component.setPreferredSize(new Dimension(iWidth, component
					.getSize().height));
		}
	}
	
	/**
	 * ����γ�����귢���仯ʱ��ִ�д˶�������ȡ��ͼƬ�����ػ�
	 */
	private void centerChanged(){
		String centerStr = getLocationTextFiled().getText();
		double[] d = GeofencingUtils.centerStr2Double(centerStr);
		if(null!=d && d.length > 0){
			restPicByNewCenter(d[0], d[1]);
		}
	}
	
	/**
	 *��߱仯�󣬸���ͼƬ
	 * @param longitude
	 * @param lagtitude
	 */
	private void restPicByNewCenter(double longitude, double lagtitude){
		try {
			getImagePanel().setPath(AutoCompleter.buildImageUrl(longitude, lagtitude));
		} catch (BusinessException e) {
			Logger.error(e.toString());
		}
	}
	
	protected void initButton() {
		getButtonPanel().add(getScaleLabel());
		getButtonPanel().add(getScaleComboBox());
	}

	/**
	 * ����ԲȦ�İ뾶��ֵ�����ı䣬ִ�д˶������������Բ��ֱ�����ػ�
	 */
	private void radiusChanged(){
		String radiusStr = radTextFiled.getText();
		int radius = 0;
		int scale = getScale();
		try {
			radius = Integer.valueOf(radiusStr);
		} catch (NumberFormatException e1) {
			Logger.error(e1.toString());
			radius = 0;
		}
		if (0 != radius && 0 != scale) {
			setOvalSize(2 * SCALE * radius / scale);
			getImagePanel().setOvalSize(getOvalSize());
		}
	}
	
	/**
	 * ��ȡ�����ߵ�λ����λ�ף���ͨ����������Ͽ�ѡ��֮�󷵻ؾ���ı����ߵ�ֵ<br>
	 * ͼ��1cm,����Ϊ85�����أ�SCALE��<br>
	 * <li>0:20m/cm</li>
	 * <li>1:50m/cm</li>
	 * <li>2:100m/cm</li>
	 * <li>3:200m/cm</li>
	 * <li>4:500m/cm</li>
	 * <li>5:1km/cm</li>
	 * <li>6:2km/cm</li>
	 * <li>7:5km/cm</li>
	 * <li>8:10km/cm</li>
	 * <li>9:20km/cm</li>
	 * <li>10:25km/cm</li>
	 * <li>11:50km/cm</li>
	 * <li>12:100km/cm</li>
	 * @return int
	 */
	public int getScale(){
		int index = getScaleComboBox().getSelectedIndex();
		switch (index) {
			case 0: return 20;	case 1: return 50;
			case 2: return 100;	case 3: return 200;
			case 4: return 500;	case 5: return 1000;
			case 6: return 2000;	case 7: return 5000;
			case 8: return 10000;	case 9: return 20000;
			case 10: return 25000;	case 11: return 50000;
			case 12: return 100000; default: return 0;
		}
	}
	
	//���������ӿڵ�ʵ��
	//KeyListener
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * �뾶�������Խ��ܻس��¼�
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		char ch = e.getKeyChar();
		// ֻ�а��»س�ʱ��ˢ��ͼƬ
		if (ch == KeyEvent.CHAR_UNDEFINED || ch != '\n') {
			return;
		}
		radiusChanged();
	}
	
	//FocusListener�뾶�����ʧȥ����ʱ���ػ�ԲȦ
	@Override
	public void focusLost(FocusEvent e) {
		radiusChanged();
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		// ��ȡ����ʱ���ݲ�����
	}
	
	//ItemListener��������Ͽ�ѡ����¼�����
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED){
			int index = scaleComboBox.getSelectedIndex();
			try {
				radiusChanged();
				getImagePanel().setPath(AutoCompleter.buildImageUrl(index));
			} catch (BusinessException e1) {
				Logger.error(e1.toString());
			}
		}
	}
	
	//ActionListener�����ѯ��ť���¼�����
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == checkBtn){
			getGeoComboBox().getCompleter().check();
		} else if(evt.getSource() == getBtnClose()){
			closeCancel();
		}
	}

	public static int getOvalSize() {
		return ovalSize;
	}

	public static void setOvalSize(int ovalSize) {
		GeofencingDialog.ovalSize = ovalSize;
	}

	public boolean isBlInited() {
		return blInited;
	}

	public void setBlInited(boolean blInited) {
		this.blInited = blInited;
	}

	public JPanel getCenterPanel() {
		if(null == centerPanel){
			centerPanel = this.createCenterPanel();
		}
		return centerPanel;
	}

	public void setCenterPanel(JPanel centerPanel) {
		this.centerPanel = centerPanel;
	}

	public JPanel getNorthPanel() {
		if(null == northPanel){
			northPanel = this.createNorthPanel();
		}
		return northPanel;
	}

	public void setNorthPanel(JPanel northPanel) {
		this.northPanel = northPanel;
	}
	
	/**
	 * �ԡ�ȡ����ģʽ�رնԻ��� ҵ��ڵ������Ҫ�޸�
	 */
	public int closeCancel() {
		close();
		return ID_CANCEL;
	}

	/**
	 * �ԡ�ȷ����ģʽ�رնԻ��� ҵ��ڵ������Ҫ�޸�
	 */
	public int closeOK() {
		close();
		return ID_OK;
	}
	
	/**
	 * This method was created by a SmartGuide. close Panel
	 */
	protected void close() {
		if (!isShowing())
			return;
		this.setVisible(false);
		if (isModal() && getDefaultCloseOperation() == DISPOSE_ON_CLOSE) {
			dispose();
		}
	}

	//MouseListener��ʵ�ַ���
	@Override
	public void mouseClicked(MouseEvent e) {
		//��ȡ��ǰ��γ��
		String centerString = AutoCompleter.getCenter();
		double d[] = GeofencingUtils.centerStr2Double(centerString);
		if (null == d || d.length == 0) {
			return;
		}
		Logger.error("ԭ��γ������" + d[0] + "," + d[1]);
		//���������λ�õ�ͼƬ����λ�þ���
		int imageWidth = getImagePanel().getWidth();
		int imageHeight = getImagePanel().getHeight();
		int x = e.getX() - imageWidth /2;
		int y = imageHeight/2 - e.getY();
		Logger.error("���λ��" + x + "," + y);
		Logger.error("γ�ȱ仯����:" + y + "���ȱ仯����:" + x);
		//���������λ�ü����µľ�γ������
		int scale = GeofencingUtils
				.getScaleByindex(getScaleComboBox().getSelectedIndex());
		double xlong = GeofencingUtils.getTudeX(x, scale);
		d[0] += xlong;
		double xlati = GeofencingUtils.getTudeX(y, scale);
		d[1] += xlati;
		Logger.error("���Ȳ�:" + xlong + ",γ�Ȳ�:" + xlati);
		//ˢ��ͼƬ
		restPicByNewCenter(d[0], d[1]);
		//��������ֵ
		getLocationTextFiled().setText(GeofencingUtils.df.format(d[0]) + "," + GeofencingUtils.df.format(d[1]));
		Logger.error("�¾�γ������:" + GeofencingUtils.df.format(d[0]) + "," + GeofencingUtils.df.format(d[1]));
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public String getLocationStr(){
		return null==this.getLocationTextFiled() ? "" : 
			this.getLocationTextFiled().getText();
	}
	
	/**
	 * ��ȡ�뾶
	 * @return
	 */
	public String getRadiusStr(){
		return null == this.getRadTextFiled() ? "" 
				: this.getRadTextFiled().getText();
	}
}
