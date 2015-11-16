package com.coolmap;

import java.io.Serializable;
import java.util.List;

import com.coolmap.MyOrientation.OrientainChanged;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity<MapView, BaiduMap> extends Activity implements android.view.View.OnClickListener{
   
	       private static final String TAG = null;
		private MapView  mapview;
       	   private BaiduMap  mapdu ;
       	   private Button   tanchu;
       	   private PopupWindow window ;
       	   private  int  flag =1;
       	   private Context context;
       	  private  TextView one;
     	  private  TextView two;
      	 private  TextView three;
      	 private TextView mylocate;
      	 private TextView   addwu;
      	 //���������
      	 private   BitmapDescriptor     mMarker;
      	 private  LinearLayout    MarkerLinear;
      	 private  LayoutInflater  inflater;
       	  //��ض�λ
       	   private  LocationClient client;
       	   private Mylocation    locationlistener;
       	   private  boolean  firsiIn  = true;   //��һ��ʹ�õ�ͼ�Ķ�λ
       	   private  double    lontitude;    //����
       	    private  double  latititude;      //γ��
       	    private  MyOrientation    myorientaion ;   //���巽�򴫸���
       	   //��λ����Ҫ����service
       	    //�Զ��嶨λͼ��
       	    private BitmapDescriptor  mylocationbitmap;
       	    private float  CurrentX ; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		 
		  tanchu = (Button)findViewById(R.id.tanchu);
		  mapview = (MapView)findViewById(R.id.bmapView);
		  this.context = this;
		  MapStatusUpdate   chengdu = MapStatusUpdateFactory.zoomTo(15.0f);
		  mapdu = mapview.getMap();
		  mapdu.setMapStatus(chengdu);
	  	 tanchu.setOnClickListener(this);
		  initLocation();
		  initMapWu();
		
		
	  	  mapdu.setOnMarkerClickListener(new OnMarkerClickListener() {
				
				public boolean onMarkerClick(Marker marker) {
					// TODO Auto-generated method stub
					 Bundle   bundle =  marker.getExtraInfo();     //���Marker�е���Ϣ
				//	   info    dedao       =  (info) bundle.getSerializable("info");
				//	   Toast.makeText(context, dedao.getName(), 0).show();
				 Intent  intent = new Intent();
		     	 intent.setClass(context, detail_content.class);
				 intent.putExtras(bundle);      //����bundle��ֵ
			/* ����������ʾ������ĵ���λ��
			 InfoWindow  infowindow ;
			 TextView  text = new TextView(context);
			 text.setBackgroundResource(getResources().getDrawable(R.drawable.));
		     text.setPadding(30, 20, 30, 50);
		     info    dedao       =  (info) bundle.getSerializable("info");     
				 text.setText(dedao.getName());
				 //final�������ܱ�����
				 final    LatLng  latlng =  marker.getPosition();   //�õ��˱�ǵľ�γ��
		    	Point  p  = mapdu.getProjection().toScreenLocation(latlng)	; //����γ��ת������Ļ�ϵĵ�
				  p.y -= 47;
			  LatLng   ii = mapdu.getProjection().fromScreenLocation(p);  //�ѵ�ת���ɾ�γ��
			    infowindow = new InfoWindow(text, ii,  (Integer) null);*/
					
			  startActivity(intent);
			  
			
					return true;
				}
			});
		/*	  mapdu.setOnMapClickListener(new OnMapClickListener() {
				
				public boolean onMapPoiClick(MapPoi arg0) {
					// TODO Auto-generated method stub
					return false;
				}
				
				public void onMapClick(LatLng arg0) {
					// TODO Auto-generated method stub
					        MarkerLinear.setVisibility(View.INVISIBLE);   //��ʧ
				}
			});*/
	}  
	//�������ʼ��
	    private void initMapWu() {
		// TODO Auto-generated method stub
		     mMarker   = BitmapDescriptorFactory.fromResource(R.drawable.dingweitubiao);     //��ö�λ��ͼƬ
	  
	         
	    }
		private void initLocation() {
		// TODO Auto-generated method stub
		          client = new LocationClient(this);
		          locationlistener = new Mylocation();
		          client.registerLocationListener(locationlistener);
		          //����location��һЩ����
		          LocationClientOption  option = new LocationClientOption();
		          option.setCoorType("bd09ll");
		          option.setIsNeedAddress(true);       //��õ�ǰ�ĵ�ַ
		          option.setOpenGps(true);
		          option.setScanSpan(1000);
		          client.setLocOption(option);     //���������������
		        //��ʼ��ͼ��
		         mylocationbitmap  = BitmapDescriptorFactory.fromResource(R.drawable.arrow);
		         //��ʼ�� ���򴫸���
		         myorientaion = new MyOrientation(this);
		        myorientaion.setChanged(new OrientainChanged() {
					
					@Override
					public void Onchanged(float x) {
						// TODO Auto-generated method stub
						            CurrentX = x;
					}
				});
		          }
		@Override
	    protected void onPause() {
	    // TODO Auto-generated method stub
	    super.onPause();
	    mapview.onPause();
	    }  
		@Override
		protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mapdu.setMyLocationEnabled(true);
		if(!client.isStarted())
			   client.start();
		//�������򴫸���
		myorientaion.start();
		}
	
		@Override
		protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mapdu.setMyLocationEnabled(false);
		client.stop();
		//ֹͣ���򴫸���
				myorientaion.stop();
		}
	    @Override
	    protected void onDestroy() {
	    // TODO Auto-generated method stub
	    super.onDestroy();
	    mapview.destroyDrawingCache();
	    }
	    @Override
	    protected void onResume() {
	    // TODO Auto-generated method stub
	    super.onResume();
	       mapview.onResume();
	    }
	    
	    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		   switch (v.getId()) {
	    	case R.id.tanchu :
			  if(flag == 1){
			    	    View aaa = getLayoutInflater().inflate(R.layout.style_map, null);
			    	    window = new PopupWindow(aaa,100,150);
			    	     window.showAtLocation(aaa, Gravity.BOTTOM|Gravity.CENTER, -100,-25);
			    	    one  = (TextView)aaa.findViewById(R.id.common_map);
				        two  = (TextView)aaa.findViewById(R.id.star_map); 
				        three  = (TextView)aaa.findViewById(R.id.traffic_map);
				         mylocate = (TextView)aaa.findViewById(R.id.mylocate);
				         addwu  =(TextView)aaa.findViewById(R.id.add);
			    	    one.setOnClickListener(this);
			            two.setOnClickListener(this);
			            three.setOnClickListener(this);
			            mylocate.setOnClickListener(this);
			            addwu.setOnClickListener(this);
			    	    flag = 2;}
			  else{
				            if(window != null && window.isShowing()){
				            	 window.dismiss();
				            	 flag = 1;
				            }
			  }
			    	    
			
			
			 
			
			     break;
	    	case R.id.common_map:
			       mapdu.setMapType(BaiduMap.MAP_TYPE_NORMAL);
			       break;
		case R.id.star_map:
			       mapdu.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
		        	break;
			      
		case R.id.traffic_map:
			     if( mapdu.isTrafficEnabled()){
			    	   mapdu.setTrafficEnabled(false);
			     }
			     else{
			    	  mapdu.setTrafficEnabled(true);
			     }
			      break;
		case R.id.mylocate:
			  LatLng lat = new LatLng(latititude, lontitude);//��þ��Ⱥ�γ��
       	     MapStatusUpdate    map = MapStatusUpdateFactory.newLatLng(lat);
       	      mapdu.animateMapStatus(map);    //��ͼ��ǰλ���Ѷ�������ʽչ��      
			break;
		   //���Ӹ����� ����
		 case R.id.add:
			     info ii = new info();
			     ii.jianli();
			       AddOverly(ii.hhh);
;			      break;
			}
	}
	/**
	 * ���Ӹ�����
	 * @param hhh
	 */
	private void AddOverly(List<info> hhh) {
		// TODO Auto-generated method stub
	          	 mapdu.clear();   //���ͼ���ϵĶ���
		         //���徭γ��
		         LatLng  latlng = null;
		         Marker marker = null;
		         OverlayOptions   options;
		         //����һ��ѭ��
		         for(info  nihao :  hhh){
		        	  //��γ��      
		        	 latlng = new LatLng(nihao.getLatitude(),nihao.getLongitude());
		        	  //ͼ��
		        	 options = new MarkerOptions().position(latlng).icon(mMarker).zIndex(5);      //���ӵ�ͼ�����߲�
		        	 marker = (Marker) mapdu.addOverlay(options);
		        	 //markerЯ��һЩֵ
		        	 Bundle bundle = new Bundle();
		        	 bundle.putSerializable("info", nihao);       //ʵ��������
		        	 marker.setExtraInfo(bundle);                       //marker Я����ϢҲ����info����е���Ϣ
		 
		        	
		        	 
		         }
		     //ÿ��������ͼ���ʱ�򣬰ѵ�ͼ�ƶ�����һ��ͼ���λ��
		         	MapStatusUpdate  msu = MapStatusUpdateFactory.newLatLng(latlng);
		         	mapdu.setMapStatus(msu);
	}
	private  class Mylocation implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			        MyLocationData   data = new MyLocationData.Builder()//
			                                                                .direction(CurrentX)//
			                                                             .accuracy(location.getRadius())//
			                                                             .latitude(location.getLatitude())//
			                                                            .longitude(location.getLongitude())//
			                                                            .build();
			        MyLocationConfiguration  config =  new MyLocationConfiguration(com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.NORMAL, true, mylocationbitmap);
			        mapdu.setMyLocationConfigeration(config);
			         mapdu.setMyLocationData(data);
			      //���¾�γ��
			         latititude = location.getLatitude();     //�ѵ�ǰ�ľ��ȱ���
			         lontitude = location.getLongitude();  //�ѵ�ǰ��γ�ȱ���
			         if(firsiIn){
			        	   LatLng lat = new LatLng(location.getLatitude(), location.getLongitude());//��þ��Ⱥ�γ��
			        	   MapStatusUpdate    map = MapStatusUpdateFactory.newLatLng(lat);
			        	   mapdu.animateMapStatus(map);    //��ͼ��ǰλ���Ѷ�������ʽչ��
			        	   firsiIn = false;
			        	   
			        	   Toast.makeText(context, location.getAddrStr(), Toast.LENGTH_SHORT).show();   //�ѵ�ǰ�����ڵ�λ��show����
			        }
			    }
		}
		
	}
