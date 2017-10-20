package com.ys.pa200.ui.homeui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ys.pa200.R;
import com.ys.pa200.bean.CheckProgrem;
import com.ys.pa200.bean.Patient;
import com.ys.pa200.bean.PicData;
import com.ys.pa200.dialog.FileDialog;
import com.ys.pa200.ui.baseui.BaseActivity;
import com.ys.pa200.utils.BitmapUtils;
import com.ys.pa200.utils.MyToast;
import com.ys.pa200.utils.SDCUtils;
import com.ys.pa200.utils.StatusBarUtil;
import com.ys.pa200.utils.ToastUtils;
import com.ys.pa200.weight.UsImageView;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leltek.viewer.model.Probe;
import leltek.viewer.model.WifiProbe;

/**
 * 超声扫描的activity
 */
public class ConnectUltrasonicActivity extends BaseActivity implements Probe.ScanListener, Probe.CineBufferListener, Probe.BatteryListener, Probe.TemperatureListener
{
	@BindView(R.id.home_connectultconnect_textone)
	TextView home_connectultconnect_textone;

	@BindView(R.id.home_connectultconnect_bianhao)
	TextView home_connectultconnect_bianhao;

	@BindView(R.id.image_usview)
	UsImageView image_usview;
	@BindView(R.id.home_connectultconnect_file)
	Button home_connectultconnect_file;
	@BindView(R.id.home_connectultconnect_textlv)
	TextView home_connectultconnect_textlv;
	@BindView(R.id.home_connectultconnect_temp)
	TextView home_connectultconnect_temp;
	//B模式和cf模式
	@BindView(R.id.home_connectultconnect_b)
	TextView home_connectultconnect_B;
	@BindView(R.id.home_connectultconnect_cf)
	TextView home_connectultconnect_cf;

	//关闭超声和合适的宽高
	@BindView(R.id.home_connectultconnect_close)
	Button home_connectultconnect_close;
	@BindView(R.id.home_connectultconnect_wh)
	Button home_connectultconnect_wh;

	// Gian按钮
	@BindView(R.id.home_connectultconnect_gianadd)
	Button home_connectultconnect_gianadd;
	@BindView(R.id.home_connectultconnect_gianlow)
	Button home_connectultconnect_gianlow;

	// dr动态范围
	@BindView(R.id.home_connectultconnect_dradd)
	Button home_connectultconnect_dradd;
	@BindView(R.id.home_connectultconnect_drlow)
	Button home_connectultconnect_drlow;

	// 持久
	@BindView(R.id.home_connectultconnect_cjadd)
	Button home_connectultconnect_cjadd;
	@BindView(R.id.home_connectultconnect_cjlow)
	Button home_connectultconnect_cjlow;
	TextView home_connectultconnect_cjtext;

	// 增强图像
	@BindView(R.id.home_connectultconnect_zqadd)
	Button home_connectultconnect_zqadd;
	@BindView(R.id.home_connectultconnect_zqlow)
	Button home_connectultconnect_zqlow;
	// 图像值
	@BindView(R.id.home_connectultconnect_tuxiang)
	TextView home_connectultconnect_tuxiang;


	@BindView(R.id.home_connectultconnect_giantext)
	TextView home_connectultconnect_giantext;

	@BindView(R.id.home_connectultconnect_dongtaitext)
	TextView home_connectultconnect_dongtaitext;

	// 深度
	@BindView(R.id.home_connectultconnect_dep32)
	Button home_connectultconnect_dep32;

	@BindView(R.id.home_connectultconnect_dep63)
	Button home_connectultconnect_dep63;

	@BindView(R.id.home_connectultconnect_dep126)
	Button home_connectultconnect_dep126;

	@BindView(R.id.home_connectultconnect_dep189)
	Button home_connectultconnect_dep189;

	@BindView(R.id.home_connectultconnect_depthtext)
	TextView home_connectultconnect_depthtext;

	@BindView(R.id.home_connectultconnect_dongjie)
	ImageView home_connectultconnect_dongjie;


	private FileDialog fileDialog;
	private Probe probe;
	private CheckProgrem cp; //检查项目
	private Patient patient; //病人信息

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.activity_home_connectultrasonic);
		initView();
		init();
		setStatusBar();
	}

	private void init()
	{
		ButterKnife.bind(this);
//		image_usview = new UsImageView(this);
		fileDialog = new FileDialog(this , listener);
		probe = WifiProbe.getDefault();
		probe.setScanListener(this);
		probe.setCineBufferListener(this);
		probe.setBatteryListener(this);
		probe.setTemperatureListener(this);

		//进来开始扫面
//		probe.startScan();
		//初始化设定Gain为50
		probe.setGain(50);
		//TCG的初始值设定一个最完美区域
		probe.setTgc1(50);
		probe.setTgc2(50);
		probe.setTgc3(50);
		probe.setTgc4(50);

		// 脉冲重复频率值越高越清晰，作用域越小
		probe.setColorPrf(4);

		probe.setGrayMap(probe.getGrayMapMaxValue() / 2);

		//初始动态值

		probe.setGain(50);

		probe.setDr(50);

		//设定持久初始值0-4
		probe.setPersistence(2);

		probe.setColorAngle(3);

		// 设定增强图像
		probe.setEnhanceLevel(2);
		home_connectultconnect_cjtext.setText("持久: " + probe.getPersistence());
		home_connectultconnect_textlv.setText("电量:" + probe.getBatteryLevel() + "%");
		home_connectultconnect_temp.setText("温度: " + probe.getTemperature());

		isShowEnhText();
		int a = probe.getGain();
		home_connectultconnect_giantext.setText(a + "");
		home_connectultconnect_dongtaitext.setText(probe.getDr() + "");

		//设定深度
		probe.setDepth(Probe.EnumDepth.LinearDepth_32);
		home_connectultconnect_depthtext.setText(probe.getDepth() + "mm");
		Intent intent = getIntent();
		patient = (Patient) intent.getSerializableExtra("patient");
		//添加病人信息
		if (patient == null){
			patient = new Patient();
		}else {
			patient = DataSupport.find(Patient.class,patient.getId(),true);
		}
	}

	private void isShowEnhText()
	{
		if(probe.getEnhanceLevel() < 2 && probe.getEnhanceLevel() >= 0)
		{
			home_connectultconnect_tuxiang.setText("低");
		}
		else if(probe.getEnhanceLevel() < 3 && probe.getEnhanceLevel() >= 2)
		{
			home_connectultconnect_tuxiang.setText("中");
		}
		else if(probe.getEnhanceLevel() <= 4 && probe.getEnhanceLevel() >= 3)
		{
			home_connectultconnect_tuxiang.setText("高");
		}
	}

	private void initView()
	{
		home_connectultconnect_cjtext = (TextView) findViewById(R.id.home_connectultconnect_cjtext);
	}


	@Override
	public void setStatusBar()
	{
		StatusBarUtil.setTransparent(this);
	}

	// 电池水平改变
	@Override
	public void onBatteryLevelChanged(int newBatteryLevel)
	{
		home_connectultconnect_textlv.setText("电量:" + newBatteryLevel + "%");
	}

	// 电池电量低
	@Override
	public void onBatteryLevelTooLow(int BatteryLevel)
	{
		if(BatteryLevel <= 30)
		{
			ToastUtils.showString(ConnectUltrasonicActivity.this , "电量太低，请及时充电");
		}
	}


	// 温度改变
	@Override
	public void onTemperatureChanged(int newTemperature)
	{
		home_connectultconnect_temp.setText("温度: " + newTemperature);

	}

	// 温度过热
	@Override
	public void onTemperatureOverHeated(int temperature)
	{
		ToastUtils.showString(ConnectUltrasonicActivity.this , "温度太高了,请让设备休息一下");
	}

	@Override
	public void onCineBufferCountIncreased(int newCineBufferCount) {

	}

	@Override
	public void onCineBufferCleared() {

	}

	@Override
	public void onModeSwitched(Probe.EnumMode mode)
	{
		if (mode == Probe.EnumMode.MODE_B)
		{
//			MyToast.getInstance(ConnectUltrasonicActivity.this).show( "B模式开启" , 1);
		}
		else if (mode == Probe.EnumMode.MODE_C)
		{
//			MyToast.getInstance(ConnectUltrasonicActivity.this).show("CF模式开启" , 1);
		}
	}

	@Override
	public void onModeSwitchingError()
	{
		ToastUtils.showString(ConnectUltrasonicActivity.this , "切换模式失败");
	}

	@Override
	public void onConnectionError() {
		ToastUtils.showString(ConnectUltrasonicActivity.this , "连接关闭");
//		finish();
	}

	@Override
	public void onScanStarted() {

	}

	@Override
	public void onScanStopped() {

	}


	/**
	 * 設定depth成功
	 *
	 * @param newDepth newDepth
	 */
	@Override
	public void onDepthSet(Probe.EnumDepth newDepth)
	{
		probe.setDepth(newDepth);
		MyToast.getInstance(ConnectUltrasonicActivity.this).show("设定成功" , 1);
	}

	@Override
	public void onDepthSetError()
	{
		MyToast.getInstance(ConnectUltrasonicActivity.this).show("设定深度失败" , 1);
	}

	@Override
	public void onImageBufferOverflow() {

	}

	/**
	 * 取消的dialog
	 */
	private void dimissContinuePayDialog()
	{
		if (fileDialog != null)
		{
			fileDialog.commitText(home_connectultconnect_textone , home_connectultconnect_bianhao);
		}
	}

	private FileDialog.OnDialogClickListener listener = new FileDialog.OnDialogClickListener()
	{

		@Override
		public void continuePay()
		{
			dimissContinuePayDialog();
			patient = fileDialog.getPatient();
		}

		@Override
		public void cancel()
		{
			dimissContinuePayDialog();
		}
	};

	@OnClick({R.id.home_connectultconnect_file , R.id.home_connectultconnect_b , R.id.home_connectultconnect_cf
			, R.id.home_connectultconnect_close , R.id.home_connectultconnect_wh
			, R.id.home_connectultconnect_gianadd , R.id.home_connectultconnect_gianlow
			, R.id.home_connectultconnect_dradd , R.id.home_connectultconnect_drlow
			, R.id.home_connectultconnect_cjadd , R.id.home_connectultconnect_cjlow
			, R.id.home_connectultconnect_zqadd , R.id.home_connectultconnect_zqlow
			, R.id.home_connectultconnect_dep32 , R.id.home_connectultconnect_dep63
			, R.id.home_connectultconnect_dep126 , R.id.home_connectultconnect_dep189
			, R.id.home_connectultconnect_dongjie,R.id.home_savebitmap_btn})
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.home_connectultconnect_file:
				fileDialog.show();
				break;

			case R.id.home_connectultconnect_b:
				MyToast.getInstance(ConnectUltrasonicActivity.this).show("切换B模式" , 1);
				probe.swithToBMode();
				break;

			case R.id.home_connectultconnect_cf:
				MyToast.getInstance(ConnectUltrasonicActivity.this).show("切换C模式开启" , 1);
				probe.swithToCMode();
				break;

			case R.id.home_connectultconnect_close:
				if (probe.isLive())
				{
					probe.stopScan();
				}
				ConnectUltrasonicActivity.this.finish();
				break;

			case R.id.home_connectultconnect_wh:
				switchFit();
				break;

			case R.id.home_connectultconnect_gianadd:
				if(probe.getGain() >= 0 && probe.getGain() <= 100)
				{
					if(probe.getGain() == 100)
					{
						MyToast.getInstance(ConnectUltrasonicActivity.this).show("已调到最大值" , 1);
						return;
					}
					probe.setGain(probe.getGain() + 10);
					home_connectultconnect_giantext.setText(probe.getGain() + "");
				}
				break;

			case R.id.home_connectultconnect_gianlow:
				if(probe.getGain()>= 10 && probe.getGain() <= 100)
				{
					probe.setGain(probe.getGain() - 10);
					home_connectultconnect_giantext.setText(probe.getGain() + "");
				}
				if(probe.getGain() == 0)
				{
					MyToast.getInstance(ConnectUltrasonicActivity.this).show("已调到最小值" , 1);
					return;
				}
				break;

			//动态范围
			case R.id.home_connectultconnect_drlow:
				if(probe.getDr()>= 10 && probe.getDr() <= 100)
				{
					probe.setDr(probe.getDr() - 10);
					home_connectultconnect_dongtaitext.setText(probe.getDr() + "");
				}
				if(probe.getDr() == 0)
				{
					MyToast.getInstance(ConnectUltrasonicActivity.this).show("已调到最低范围" , 1);
					return;
				}
				break;

			case R.id.home_connectultconnect_dradd:
				if(probe.getDr() >= 0 || probe.getDr() <= 100)
				{
					if(probe.getDr()  == 100)
					{
						MyToast.getInstance(ConnectUltrasonicActivity.this).show("已调到最大范围" , 1);
						return;
					}
					probe.setDr(probe.getDr() + 10);
					home_connectultconnect_dongtaitext.setText(probe.getDr() + "");
				}
				break;

			//持久
			case R.id.home_connectultconnect_cjadd:
				if(probe.getPersistence() >= 0 || probe.getPersistence() <= 4)
				{
					if(probe.getPersistence() == 4)
					{
						MyToast.getInstance(ConnectUltrasonicActivity.this).show("已调到最大持久值" , 1);
						return;
					}
					probe.setPersistence(probe.getPersistence() + 1);
					home_connectultconnect_cjtext.setText("");
					home_connectultconnect_cjtext.setText("持久: " + probe.getPersistence());
				}
				break;

			case R.id.home_connectultconnect_cjlow:
				if(probe.getPersistence()>= 1 && probe.getPersistence() <= 4)
				{
					probe.setPersistence(probe.getPersistence() - 1);
					if(probe.getPersistence() == 0)
					{
						MyToast.getInstance(ConnectUltrasonicActivity.this).show("已调到最小持久值" , 1);
						return;
					}
					home_connectultconnect_cjtext.setText("持久: " + probe.getPersistence());
				}
				break;

			//图像增强
			case R.id.home_connectultconnect_zqadd:
				MyToast.getInstance(ConnectUltrasonicActivity.this).show("已调到最大图像" , 1);
				if(probe.getEnhanceLevel() >= 0 || probe.getEnhanceLevel() <= 4)
				{
					if(probe.getEnhanceLevel() == 4)
					{
						MyToast.getInstance(ConnectUltrasonicActivity.this).show("已调到最大图像" , 1);
						return;
					}
					probe.setEnhanceLevel(probe.getEnhanceLevel() + 1);
					isShowEnhText();
				}
				break;

			case R.id.home_connectultconnect_zqlow:
				MyToast.getInstance(ConnectUltrasonicActivity.this).show("已调到最小图像" , 1);
				if(probe.getEnhanceLevel()>= 1 && probe.getEnhanceLevel() <= 4)
				{
					if(probe.getEnhanceLevel() == 0)
					{
						MyToast.getInstance(ConnectUltrasonicActivity.this).show("已调到最小图像" , 1);
						return;
					}
					probe.setEnhanceLevel(probe.getEnhanceLevel() - 1);
					isShowEnhText();
				}
				break;

			case R.id.home_connectultconnect_dep32:
				probe.setDepth(Probe.EnumDepth.LinearDepth_32);
				home_connectultconnect_depthtext.setText(probe.getDepth() + "mm");
				break;

			case R.id.home_connectultconnect_dep63:
				probe.setDepth(Probe.EnumDepth.LinearDepth_63);
				home_connectultconnect_depthtext.setText(probe.getDepth() + "mm");
				break;

			case R.id.home_connectultconnect_dep126:
				probe.setDepth(Probe.EnumDepth.ConvexDepth_126);
				home_connectultconnect_depthtext.setText(probe.getDepth() + "mm");
				break;

			case R.id.home_connectultconnect_dep189:
				probe.setDepth(Probe.EnumDepth.ConvexDepth_189);
				home_connectultconnect_depthtext.setText(probe.getDepth() + "mm");
				break;
			case R.id.home_connectultconnect_dongjie:
					if (probe.isLive())
					{
						probe.stopScan();
					}
					else
					{
						probe.startScan();
					}
				break;
			case R.id.home_savebitmap_btn:
				//截图
				saveBitmap();
				break;
			default:
				break;
		}
	}


	//设定合适宽高
	private void switchFit()
	{
		image_usview.switchFit();
		if (image_usview.isFitWidth())
		{
			home_connectultconnect_wh.setText("合适高度");
		}
		else
		{
			home_connectultconnect_wh.setText("合适宽度");
		}
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		if (probe.getMode() == Probe.EnumMode.MODE_C)
		{
			probe.swithToBMode();
		}
		if (probe.getMode() == Probe.EnumMode.MODE_B && !probe.isLive())
		{
			probe.startScan();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		probe.stopScan();
	}

	@Override
	public void onNewFrameReady(Probe.Frame frame, Bitmap bitmap)
	{
		if (frame.mode == probe.getMode()) {
			image_usview.setImageBitmap(bitmap);
		}

		Probe.CModeFrameData cModeFrameData = frame.cModeFrameData;
		if (cModeFrameData != null) {
			image_usview.setParams(cModeFrameData.originXPx, cModeFrameData.originYPx,
					cModeFrameData.rPx);
		}
	}

	@Override
	protected void onPostResume()
	{
		super.onPostResume();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
		{
			if (probe.isLive())
			{
				probe.stopScan();
			}
			ConnectUltrasonicActivity.this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 添加病人信息到数据库
	 * @param path
	 */
	private void savePatientInfo(String path)
	{
		File file = new File(path);
		if (file.exists() && file.isFile()){
			//保存图片
			PicData picdata = new PicData();
			picdata.setDate(new Date().getTime());
			picdata.setPath(path);
			picdata.save();
			//检查部位
			if (cp ==null){
				cp = new CheckProgrem();
			}
			cp.setBody(fileDialog.getCheckBody());
			cp.setDate(new Date().getTime());
			cp.getPic().add(picdata);
			cp.save();
			patient.getCheckProgrems().add(cp);
			patient.save();
		}else{
			MyToast.getInstance(this).show("截图失败，请重试",0);
		}
	}

	/**
	 * 保存图片到本地
	 */
	private void saveBitmap() {
		Bitmap currentBitmap = BitmapUtils.convertViewToBitmap(image_usview);
		if (BitmapUtils.getBitmapSize(currentBitmap) < SDCUtils.getSDFreeSize())
		{
			String path = BitmapUtils.saveBitmapInExternalStorage(currentBitmap, this);
			savePatientInfo(path);
		}else {
			MyToast.getInstance(this).show("内存不足，请删除文件后再保存",0);
		}
	}

}
