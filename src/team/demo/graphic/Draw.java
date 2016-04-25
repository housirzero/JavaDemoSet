package team.demo.graphic;

import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Draw extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Draw()
	{
		add(new X2FunctionPanel());
	}

	public static void main(String[] args)
	{
		Draw frame=new Draw();
		frame.setSize(400,250);
		frame.setTitle("绘制y=x2的函数");
		frame.setLocationRelativeTo(null);//center
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

class X2FunctionPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		//画x轴
		g.drawLine(20,150,getWidth()-20,150);
		//x箭头
		g.drawLine(getWidth()-30,140,getWidth()-20,150);
		g.drawLine(getWidth()-30,160,getWidth()-20,150);
		//“x”
		g.drawString("X",getWidth()-10,150);


		//画y轴
		g.drawLine(200,getHeight()-20,200,20);
		g.drawLine(190,30,200,20);
		g.drawLine(210,30,200,20);
		g.drawString("Y",220,30);


		//画函数图像
		Polygon p=new Polygon();
		double scaleFactor=0.01;
		for(int x=-100;x<=100;x++)
		{
			p.addPoint(x+200,150-(int)(scaleFactor*x*x));
		}
		
		g.drawPolyline(p.xpoints,p.ypoints,p.npoints);
	}

	/*public Dimension getPreferredSize()
	{
		return new Dimension(200,200);
	}*/
}
