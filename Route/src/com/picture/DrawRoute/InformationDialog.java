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

import javax.swing.JButton; // 按钮
import javax.swing.JComboBox; // 下拉列表组件
import javax.swing.JDialog; // 对话框， 没有最大化、最小化，只有退出键
import javax.swing.JLabel; // JLabel 对象可以显示文本、图像或同时显示二者
import javax.swing.JPanel; // JPanel 是对窗体中具有相同逻辑功能的组件进行组合
import javax.swing.JTextField; // JTextField  是一个轻量级组件，它允许编辑单行文本
import javax.swing.UIManager;

public abstract class InformationDialog extends JDialog implements ActionListener, 
KeyListener, ItemListener, FocusListener, MouseListener{

	private static final long serialVersionUID = 1176732739988478488L;
	public static final int SCALE = 70; //比例尺一个单位约为75个像素
	private JPanel centerPanel; // 中Panel
	private JPanel northPanel; // 上Panel 
	private JPanel buttonPanel; // 按钮Panel
	protected JButton btnClose = null; // 取消按鈕
	private JLabel latitudeLabel; // 地点纬度坐标提示语
	private JLabel longitudeLabel; // 地点经度坐标提示语 
	private JTextField latitudeTextFiled; // 显示纬度坐标
	private JTextField longitudeTextFiled; // 显示经度坐标
	private JLabel scaleLabel; // 比例尺提示语
	private JComboBox scaleComboBox; // 显示比例尺
    private boolean blInited = false; // 是否已经初始化
	public final static int ID_OK = 1;
	public final static int ID_CANCEL = 2;
    
	public InformationDialog() {
		super();
		initUI();                           // 实例化
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
            public void paint(Graphics g)      // Graphics 给paint（）提供画笔
            {
                super.paint(g);
                //  最上面画一个黑线
                g.setColor(UIManager.getColor("MessageDialog.linecolor"));
                g.drawLine(0, 0, getWidth(), 0);
            }
        };
		centerPanel.setLayout(null);     // Layout(布局管理器) 指定组件的摆放位置
		return centerPanel;
	}

	protected JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel() {
				@Override
				public void paint(Graphics g) {
					super.paint(g);
					// 最上面画一个黑线
					g.setColor(UIManager.getColor("MessageDialog.linecolor"));
					g.drawLine(0, 0, getWidth(), 0);
				}
			};
			// 添加背景颜色
			buttonPanel.setBackground(Color.LIGHT_GRAY);
			FlowLayout flowLayout = new FlowLayout(FlowLayout.RIGHT, 8, 8);//右对齐，水平间隙8，垂直间隙8
			buttonPanel.setLayout(flowLayout);
		}
		return buttonPanel;
	}
	
    public JButton getBtnClose(){
        if (btnClose == null){
        	btnClose = new JButton("关闭");
        	btnClose.setToolTipText("(Alt+" + 'C' + ")");
        	btnClose.setMnemonic('C');		
        	btnClose.setBounds(330, 8, 75, 20);
        	btnClose.addActionListener(this);
        }
        return btnClose;
    }
	
	
	
	private JLabel getLatitudeLabel() {
		if (latitudeLabel == null) {
			latitudeLabel = new JLabel("经度：");
			latitudeLabel.setPreferredSize(new Dimension(40, 20)); // 设定最佳显示大小
			latitudeLabel.setFont(new Font("dialog", 0, 14));// 逻辑字体，0平常样式、1为粗体 ，字号
			latitudeLabel.setBounds(5, 40, 40, 20); // 设置 组件尺寸和位置（坐标x，坐标y，窗口的宽度，窗口的高度 ）
		}
		return latitudeLabel;
	}
	
	private JLabel getLongitudeLabel() {
		if (longitudeLabel == null) {
			longitudeLabel = new JLabel("经度：");
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
			scaleLabel = new JLabel("比例尺:");
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
	 * 初始化类。
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
	 * 当经纬度坐标发生变化时，执行此动作，获取新图片，并重绘
	 */
	private void centerChanged(){
		String centerStr = getLocationTextFiled().getText();
		double[] d = GeofencingUtils.centerStr2Double(centerStr);
		if(null!=d && d.length > 0){
			restPicByNewCenter(d[0], d[1]);
		}
	}
	
	/**
	 *左边变化后，更新图片
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
	 * 当画圆圈的半径的值发生改变，执行此动作，计算出新圆的直径并重绘
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
	 * 获取比例尺单位（单位米），通过比例尺组合框，选择之后返回具体的比例尺的值<br>
	 * 图中1cm,大致为85个像素（SCALE）<br>
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
	
	//几个监听接口的实现
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
	 * 半径输入框可以接受回车事件
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		char ch = e.getKeyChar();
		// 只有按下回车时才刷新图片
		if (ch == KeyEvent.CHAR_UNDEFINED || ch != '\n') {
			return;
		}
		radiusChanged();
	}
	
	//FocusListener半径输入框失去焦点时，重绘圆圈
	@Override
	public void focusLost(FocusEvent e) {
		radiusChanged();
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		// 获取焦点时，暂不处理
	}
	
	//ItemListener比例尺组合框选择后事件处理
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
	
	//ActionListener点击查询按钮后事件处理
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
	 * 以‘取消’模式关闭对话框 业务节点根据需要修改
	 */
	public int closeCancel() {
		close();
		return ID_CANCEL;
	}

	/**
	 * 以‘确定’模式关闭对话框 业务节点根据需要修改
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

	//MouseListener的实现方法
	@Override
	public void mouseClicked(MouseEvent e) {
		//获取当前经纬度
		String centerString = AutoCompleter.getCenter();
		double d[] = GeofencingUtils.centerStr2Double(centerString);
		if (null == d || d.length == 0) {
			return;
		}
		Logger.error("原经纬度坐标" + d[0] + "," + d[1]);
		//计算鼠标点击位置到图片中心位置距离
		int imageWidth = getImagePanel().getWidth();
		int imageHeight = getImagePanel().getHeight();
		int x = e.getX() - imageWidth /2;
		int y = imageHeight/2 - e.getY();
		Logger.error("点击位置" + x + "," + y);
		Logger.error("纬度变化像素:" + y + "经度变化像素:" + x);
		//根据鼠标点击位置计算新的经纬度坐标
		int scale = GeofencingUtils
				.getScaleByindex(getScaleComboBox().getSelectedIndex());
		double xlong = GeofencingUtils.getTudeX(x, scale);
		d[0] += xlong;
		double xlati = GeofencingUtils.getTudeX(y, scale);
		d[1] += xlati;
		Logger.error("经度差:" + xlong + ",纬度差:" + xlati);
		//刷新图片
		restPicByNewCenter(d[0], d[1]);
		//更新坐标值
		getLocationTextFiled().setText(GeofencingUtils.df.format(d[0]) + "," + GeofencingUtils.df.format(d[1]));
		Logger.error("新经纬度坐标:" + GeofencingUtils.df.format(d[0]) + "," + GeofencingUtils.df.format(d[1]));
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
	 * 获取半径
	 * @return
	 */
	public String getRadiusStr(){
		return null == this.getRadTextFiled() ? "" 
				: this.getRadTextFiled().getText();
	}
}
