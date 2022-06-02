/**
 * 
 */
package version1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import version1.Constent.StyleEnum;
/**
 * @author 郭晟
 * @function
 * 风格菜单栏监听
 * 修改风格和保存风格
 * @time 2019-1-12
 * @value
 * 三个风格菜单选项
 * private JMenuItem Dracula;
 * private JMenuItem Sublime;
 * private JMenuItem Classic;
 * 全局风格变量
 * public static StyleEnum LocalStyle = StyleEnum.Classic; 
 * @functions
 * public ThemeChooser (JMenuItem Dracula,JMenuItem Sublime,JMenuItem Classic) 构造函数，加入选项监听
 * public void actionPerformed(ActionEvent e) 判断动作并更改全局风格
 * 序列化的风格保存类
 * class saveStyle implements java.io.Serializable
 * public saveStyle(StyleEnum localStyle) 风格保存函数
 */
public class ThemeChooser implements ActionListener {
	private JMenuItem Dracula;
	private JMenuItem Sublime;
	private JMenuItem Classic;
	public static StyleEnum LocalStyle = StyleEnum.Classic; 
	public ThemeChooser (JMenuItem Dracula,JMenuItem Sublime,JMenuItem Classic)
	{
		this.Dracula=Dracula;
		this.Sublime=Sublime;
		this.Classic=Classic;		
		Dracula.addActionListener(this);
		Sublime.addActionListener(this);
		Classic.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//判断更改变量
		if(e.getSource() == this.Dracula)
		{
			
			ThemeChooser.LocalStyle = StyleEnum.Dracula;
		}else if(e.getSource() == this.Sublime)
		{
			ThemeChooser.LocalStyle = StyleEnum.Sublime;
		}else if(e.getSource() == this.Classic)
		{
			ThemeChooser.LocalStyle = StyleEnum.Classic;
		}
		//更改风格
		MainWindow.pan.changestyle();
	}

}

class saveStyle implements java.io.Serializable{
	public  StyleEnum LocalStyle=StyleEnum.Classic;

	public saveStyle(StyleEnum localStyle) {
		super();
		LocalStyle = localStyle;
	}

	
	
}