package com.picture.DrawRoute;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;


public abstract class AutoCompleter implements KeyListener, ItemListener{
	private ComboBoxModel model = null; 
	//private ResultVO[] locations = null; //	���صص���Ϣ
	private final static String imageStaticUrl = "http://api.map.baidu.com/staticimage"; //��̬ͼURL
	private static String center = "116.403874,39.914888"; //Ĭ�ϱ����찲��
	private static String width = "800"; //ͼƬ�ߴ�
	private static String height = "600";
	private static String zoom = "19"; //Ĭ������20m/cm
	private static String markers = "�찲��";
	public static ArrayList<String> zoomInfo; //������Ϣ
	
	static {
		if(null == zoomInfo){
			zoomInfo = new ArrayList<String>();
			zoomInfo.add("20m/cm"); //, "19"
			zoomInfo.add("50m/cm"); //, "18"
			zoomInfo.add("100m/cm");//, "17"
			zoomInfo.add("200m/cm");//, "16"
			zoomInfo.add("500m/cm");//, "15"
			zoomInfo.add("1km/cm"); //, "14"
			zoomInfo.add("2km/cm"); //, "13"
			zoomInfo.add("5km/cm"); //, "12"
			zoomInfo.add("10km/cm");//, "11"
			zoomInfo.add("20km/cm");//, "10"
			zoomInfo.add("25km/cm");//, "9"
			zoomInfo.add("50km/cm");//, "8"
			zoomInfo.add("100km/cm");//, "7"
		}
	}

	public AutoCompleter(GeoComboBox comboBox) {
		owner = comboBox;
		editor = (JTextField) comboBox.getEditor().getEditorComponent();
		editor.addKeyListener(this);
		model = comboBox.getModel();
		owner.addItemListener(this);
	}

	/**
	 *���ݷ��ؽ������ȡ��̬ͼƬURl
	 * @param vo
	 * @return
	 */
	private String buildImageUrl(ResultVO vo) {
		if(null == vo){
			//��ѯ�����ص���Ϣ����ʾ�հ�ͼƬ
			return "";
		}
		center = vo.getLocation().getLng() + "," + vo.getLocation().getLat();
		try {
			markers = URLEncoder.encode(vo.getName(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			markers = "";
			Logger.error(e.toString());
		}
		return buildImageUrl();
	}
	
	/**
	 * ��ȡ��̬ͼƬ��URl
	 * @return
	 */
	private static String buildImageUrl(){
		return imageStaticUrl
				+ "?center=" + center
				+ "&width=" + width
				+ "&height=" + height
				+ "&zoom=" + zoom
				+ (!StringUtils.isBlank(markers) ? "&markers=" + markers : "");
	}
	
	/**
	 * ���������꣬��ȡͼƬUrl
	 * @param longitude
	 * @param lagitude
	 * @return
	 */
	public static String buildImageUrl(double longitude, double lagitude){
		center = GeofencingUtils.df.format(longitude) + "," + GeofencingUtils.df.format(lagitude);
		return buildImageUrl();
	}
	
	
	/**
	 * ����ѡ��ı����ߣ���ȡͼƬUrl
	 * @param index
	 * @return
	 */
	public static String buildImageUrl(int index){
		index = 19 - index;
		if(7<=index && index <= 19){
			zoom = String.valueOf(index);
		}
		return buildImageUrl();
	}
	
	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
		char ch = e.getKeyChar();
		// ֻ�а��»س������ѯ��ťʱ�ŵ��õ�ͼAPI
		if (ch == KeyEvent.CHAR_UNDEFINED || ch != '\n') {
			return;
		}
		check();
	}

	/**
	 * ִ�в�ѯ����
	 */
	public void check(){
		int caretPosition = editor.getCaretPosition();
		String queryCode = editor.getText();
		if (queryCode.length() == 0)
			return;
		try {
			getPositionInfo(queryCode, caretPosition);
		} catch (BusinessException e1) {
			Logger.error(e1.toString());
		}
	}
	
	/**
	 *�Զ���ɡ�������������ݣ����б����ҵ����Ƶ���Ŀ.
	 * @throws BusinessException 
	 */
	protected void getPositionInfo(String queryCode, int caretPosition) 
			throws BusinessException {
		if (StringUtils.isBlank(queryCode)
				|| StringUtils.isBlank(queryCode = queryCode.substring(0,
						caretPosition))) {
			return;
		}
		Object[] ret = sendHttpGet(queryCode);
		if (owner != null && null!=ret && ret.length > 0) {
			model = new DefaultComboBoxModel(ret);
			owner.setModel(model);
		}
		if (null != ret && ret.length > 0) {
			if (null != locations && locations.length > 0) {
				showLocationInfo(0);
			}
			if (owner != null) {
				try {
					owner.showPopup();
				} catch (Exception ex) {
					Logger.error(ex.toString());
				}
			}
		}
	}

	/**
	 * 
	 * �ҵ����Ƶ���Ŀ, ���ҽ�֮���е��������ǰ�档
	 * 
	 * @param str
	 * @return ����������Ŀ���б�
	 * @throws BusinessException 
	 */
	protected Object[] sendHttpGet(String str) throws BusinessException {
		try {
			PlaceSuggession.parameters.put("region", URLEncoder.encode("ȫ��", "UTF-8"));
			PlaceSuggession.parameters.put("query", URLEncoder.encode(str, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new BusinessException(e.toString(), e);
		}
		String param = PlaceSuggession.getParameterStr(PlaceSuggession.parameters);
		String returnStr = HttpHelper.sendGet(PlaceSuggession.SUGGESTION_URL, param);
		JsonElement[] josnElements = PlaceSuggession.parseJsonStr(returnStr);
		 locations = PlaceSuggession.parse2ResultVOs(josnElements);
		 return locations;
	}

	/**
	 * ������Ͽ�ѡ����¼�
	 */
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			int caretPosition = editor.getCaretPosition();
			int index = owner.getSelectedIndex();
			if (caretPosition != -1) {
				try {
					editor.moveCaretPosition(caretPosition);
					if (null != locations && locations.length > 0 
							&& index >= 0 && index < locations.length) {
						showLocationInfo(index);
					}
				} catch (IllegalArgumentException ex) {
					Logger.error(ex.toString());
				} catch (BusinessException e) {
					Logger.error(e.toString());
				}
			}
		}
	}
	
	/**
	 *  ��ʾ�ص���Ϣ
	 * @param index
	 * @throws BusinessException
	 */
	private void showLocationInfo(int index) throws BusinessException {
		if (null == locations || locations.length == 0) {
			owner.getLocationTextFiled().setText("");
			owner.getImagePanel().setPath("");
			return;
		}
		String locStr = (null == locations[index] || null == locations[index]
				.getLocation()) ? "" : locations[index].getLocation()
				.toString();
		owner.getLocationTextFiled().setText(locStr);
		owner.getImagePanel().setPath(buildImageUrl(locations[index]));
	}
	
	public ResultVO[] getLocations() {
		return locations;
	}

	public void setLocations(ResultVO[] locations) {
		this.locations = locations;
	}

	public static String getCenter(){
		return center;
	}
}
