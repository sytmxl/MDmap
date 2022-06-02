package version1;

import java.util.*;
import java.util.Map.Entry;
import java.awt.*;

/**
 * @author wangxianke
 *
 */
/**
 * 1.本类接收外部传入面板，在该面板上进行底层操作； 2.双击新建主题，自动生成连接线； 3.右键手动建立曲线； 4.更新主题、连接线、曲线位置；
 * 5.移动所有主题、连接线、曲线位置； 6.删除主题及其相关连接线、曲线； 7.删除曲线。
 */
class ThemeDetect {
	/**
	 * ThemeLabel themeLabel：相关主题
	 * PaintePanel pan:传入画板
	 */
	private int distancex, distancey;
	private ThemeLabel themeLabel = null;
	private PaintePanel pan = null;

	/** 
	 * @param pan：传入的绘画板PaintePanel
	 */
	public ThemeDetect(PaintePanel pan) {
		this.pan = pan;
	}
	/**
	 * 由新建主题生成连接线，并将主题，连接线加入hashmap
	 * @param themeLabel:传入新建的主题
	 */
	public void addConnect(ThemeLabel themeLabel) {
		this.themeLabel = themeLabel;
		distancex = themeLabel.getThemeLeftX() - this.pan.getRootThemeRightX();
		distancey = Math.abs(themeLabel.getThemeMidY() - this.pan.getRootThemeMidY());
		/****** 在根主题右边 *********/
		if (distancex > 0) {
			this.addConnectHelper(Constent.addRight);

		} else if (themeLabel.getThemeRightX() < this.pan.getRootThemeLeftX()) {
			distancex = this.pan.getRootThemeLeftX() - themeLabel.getThemeRightX();
			this.addConnectHelper(Constent.addLeft);
		} else if (themeLabel != this.pan.getrootThemeLabel()) {
			this.pan.addConnectLine(this.themeLabel, null);
		}
	}

	/**
	 * 实例化手动绘制曲线对象
	 * @param themeLabel1:起点主题
	 * @param themeLabel2:终点主题
	 */
	public void addCurveLine(ThemeLabel themeLabel1, ThemeLabel themeLabel2) {
	
		Point start, end;

		start = new Point((themeLabel1.getX() + themeLabel1.getThemeRightX()) / 2, themeLabel1.getThemeMidY());
		end = new Point((themeLabel2.getX() + themeLabel2.getThemeRightX()) / 2, themeLabel2.getThemeMidY());
		/******起点主题在终点主题左边********/
		if (themeLabel1.getX() < themeLabel2.getX()) {
			start = new Point(themeLabel1.getX() + themeLabel1.getSizeX(), themeLabel1.getThemeMidY());
			end = new Point(themeLabel2.getX(), themeLabel2.getThemeMidY());
		}
		/******起点主题在终点主题右边********/
		else {
			start = new Point(themeLabel1.getX(), themeLabel1.getThemeMidY());
			end = new Point(themeLabel2.getX() + themeLabel2.getSizeX(), themeLabel2.getThemeMidY());
		}
		/******实例化曲线对象*******/
		CurveLine curveLine = new CurveLine(start, end, themeLabel1, themeLabel2);
		/*******将曲线加入起点主题曲线列表********/
		themeLabel1.addCurveLine(curveLine);
		/*******将曲线加入终点主题曲线列表********/
		themeLabel2.addCurveLine(curveLine);
		/*******将曲线加入画板曲线列表********/
		MainWindow.pan.addCurveLine(curveLine);
	}
	/**
	 * 删除当前主题，当前主题与父主题连接，以及当前主题后面孩子的连接关系
	 * @param themeLabel:传入的主题
	 */
	public void deleteConnect(ThemeLabel themeLabel) {
		Vector<CurveLine> CurveLineList = new Vector<CurveLine>();
		findAllConnect(themeLabel, CurveLineList);
		for (CurveLine curveLine : CurveLineList) {
			curveLine.getstartLabel().getCurveLineList().remove(curveLine);
			curveLine.getendLabel().getCurveLineList().remove(curveLine);
			MainWindow.pan.getcurveLineList().remove(curveLine);
			MainWindow.pan.remove(curveLine.getNode1());
			MainWindow.pan.remove(curveLine.getNode2());
		}
		this.deleteHelper(themeLabel);
	}

	/********** 找出删除节点相关的所以连接类 **********/
	void findAllConnect(ThemeLabel sourceLabel, Vector<CurveLine> CurveLineList) {
		if (sourceLabel.getCurveLineList() != null) {
			CurveLineList.remove(sourceLabel.getCurveLineList());
			CurveLineList.addAll(sourceLabel.getCurveLineList());
		}
		for (int i = 0; i < sourceLabel.getallChild().size(); i++) {
			ThemeLabel temp = sourceLabel.getallChild().get(i);
			findAllConnect(temp, CurveLineList);
		}
	}

	/**
	 * 更新主题和连接线位置
	 * @param themeLabel:被拖动主题
	 * @param deltax:x方向变化量
	 * @param deltay:y方向变化量
	 */
	public void updateConnect(ThemeLabel themeLabel, int deltax, int deltay) {
		
		/*****遍历树更新主题后所有连接关系*********/
		this.updateConnectHelper(themeLabel, deltax, deltay, true);
		/*****增加连接关系*********/
		this.addConnect(themeLabel);
		themeLabel.setRank(themeLabel.getRank());
		if (themeLabel.getallChild().size() > 0) {
			ConnectLine connectLine = this.pan.getConnectLine(themeLabel.getChild(0));
			/******* 需要从右连枝镜像到左连枝 ****/
			boolean mirrorLeft = connectLine.getStartX() < connectLine.getEndX()
					&& connectLine.getStartX() < this.pan.getRootThemeLeftX();
			/******* 需要从左连枝镜像到右连枝 ****/
			boolean mirrorRight = connectLine.getStartX() > connectLine.getEndX()
					&& connectLine.getStartX() > this.pan.getRootThemeRightX();
			if (mirrorLeft || mirrorRight) {
				/*********左右镜像********/
				this.themeLabelMirror(themeLabel, (themeLabel.getThemeLeftX() + themeLabel.getThemeRightX()) / 2, true);
			}
		}
	}

	/**
	 * @param themeLabel:大小变化的主题
	 * @param deltaSizex:宽度变化量
	 */
	public void themesizeChangeMove(ThemeLabel themeLabel, int deltaSizex) {

		int deltaX = deltaSizex;
		if (themeLabel.getallChild().size() > 0) {
			/**** 在根主题右边 ******/
			if (themeLabel.getThemeLeftX() > this.pan.getRootThemeRightX()) {
				deltaX = deltaSizex;
			}
			/**** 在根主题左边 ******/
			else if (themeLabel.getThemeRightX() < this.pan.getRootThemeLeftX()) {
				deltaX = -deltaSizex;
			}
		}
		moveChildConnectionHelper(themeLabel, deltaX, true);
	}

	/**
	 * 移动全局，拖动跟主题或者按压右键调用此方法
	 * 
	 * @param deltax 变化x坐标
	 * @param deltay 变化y坐标
	 */

	public void moveAll(int deltax, int deltay) {

		HashMap<ThemeLabel, ConnectLine> ConnectLineList = this.pan.getallConnectLine();
		Vector<CurveLine> CurveLineList = this.pan.getcurveLineList();
		/******* curveList链接 *******/
		for (int i = 0; i < CurveLineList.size(); i++) {
			CurveLine temp = CurveLineList.get(i);
			if (temp.isLive) {
				temp.updateCurveLine(deltax, deltay);
			}
		}
		/********* hashmap内的连接 *******/
		for (Entry<ThemeLabel, ConnectLine> item : ConnectLineList.entrySet()) {
			ThemeLabel key = item.getKey();
			ConnectLine val = item.getValue();
			if (key.isLive) {
				key.updateLocation(deltax + key.getX(), deltay + key.getY());

			}
			if (key.isLive && val != null && val.isLive) {
				val.setLocation(deltax + val.getStartX(), deltay + val.getStartY(), deltax + val.getEndX(),
						deltay + val.getEndY());
			}

		}
		/********* 根主题 *********/
		ThemeLabel root = this.pan.getrootThemeLabel();
		root.updateLocation(deltax + root.getX(), deltay + root.getY());

	}
	/**
	 * @param themeLabel:长度改变的主题
	 * @param deltax:长度变化量
	 * @param isRoot:当前主题是否为长度改变的主题
	 */
	private void moveChildConnectionHelper(ThemeLabel themeLabel, int deltax, boolean isRoot) {
		if (themeLabel != null) {

			if (!isRoot) {

				/****** 因为字长改变移动曲线 ******/

				for (int i = 0; i < themeLabel.getCurveLineList().size(); i++) {
					CurveLine curveLine = themeLabel.getCurveLineList().get(i);
					if (curveLine.isLive) {

						Point start = curveLine.getStartPoint();
						Point end = curveLine.getEndPoint();

						System.out.println("曲线起点" + start.x + "  " + start.y);
						System.out.println("Label顶点" + themeLabel.getX() + "  " + themeLabel.getY());
						System.out.println(
								"themeLabel大小" + themeLabel.getThemeSizeX() + "  " + themeLabel.getThemeSizeY());
						if (start.y == themeLabel.getThemeMidY()) {
							System.out.println("起点");
							curveLine.updateFirstTwoNode(deltax, 0);

						} else if ((end.x == (themeLabel.getX() + themeLabel.getSizeX()) || end.x == themeLabel.getX())
								&& end.y == themeLabel.getThemeMidY()) {
							System.out.println("终点");
							curveLine.updateLastTwoNode(deltax, 0);

						}
					}
				}
				
				/****移动连接线和主题位置****/
				ConnectLine connectLine = this.pan.getConnectLine(themeLabel);
				if (connectLine != null) {
					connectLine.setLocation(deltax + connectLine.getStartX(), connectLine.getStartY(),
							deltax + connectLine.getEndX(), connectLine.getEndY());
				}

				themeLabel.updateLocation(themeLabel.getX() + deltax, themeLabel.getY());
			}

			else {
				/****** 因为字长改变移动曲线 ******/

				for (int i = 0; i < themeLabel.getCurveLineList().size(); i++) {
					CurveLine curveLine = themeLabel.getCurveLineList().get(i);
					if (curveLine.isLive) {

						Point start = curveLine.getStartPoint();
						Point end = curveLine.getEndPoint();

						System.out.println("曲线起点" + start.x + "  " + start.y);
						System.out.println("Label顶点" + themeLabel.getX() + "  " + themeLabel.getY());
						System.out.println(
								"themeLabel大小" + themeLabel.getThemeSizeX() + "  " + themeLabel.getThemeSizeY());
						if (start.y == themeLabel.getThemeMidY()) {
							System.out.println("起点");

							if ((start.x > themeLabel.getThemeLeftX()
									&& themeLabel.getThemeLeftX() > this.pan.getRootThemeRightX())
									|| (start.x < themeLabel.getThemeRightX()
											&& themeLabel.getThemeRightX() < this.pan.getRootThemeLeftX())) {
								curveLine.updateFirstTwoNode(deltax, 0);
							}

						} else if (end.y == themeLabel.getThemeMidY()) {
							System.out.println("终点");
							if ((end.x > themeLabel.getThemeLeftX()
									&& themeLabel.getThemeLeftX() > this.pan.getRootThemeRightX())
									|| (end.x < themeLabel.getThemeRightX()
											&& themeLabel.getThemeRightX() < this.pan.getRootThemeLeftX())) {
								curveLine.updateLastTwoNode(deltax, 0);
							}

						}
					}
				}
			}

			boolean ispanRootTheme = false;
			if (themeLabel == this.pan.getrootThemeLabel()) {
				ispanRootTheme = true;
			}
			
			/****移动孩子主题及连接线****/
			for (int i = 0; i < themeLabel.getallChild().size(); i++) {
				ThemeLabel temp = themeLabel.getallChild().get(i);
				if (ispanRootTheme) {
					if (temp.getThemeRightX() > themeLabel.getThemeRightX())
						moveChildConnectionHelper(temp, deltax, false);
				} else {
					moveChildConnectionHelper(temp, deltax, false);
				}
			}

		} else {
			return;
		}

	}

	/********* 计算在根主题左边或右边 **********/
	private void addConnectHelper(int addDirection) {
		/******* 在右边 *********/
		if (addDirection > 0) {
			/********** 是根主题的childTheme ********/
			if ((distancex < Constent.minDistancex) && (distancey < Constent.minDistancey)) {
				this.pan.rootThemeaddChild(themeLabel);
				this.themeLabel.setFather(this.pan.getrootThemeLabel());
				ConnectLine connectLine = new ConnectLine(this.pan.getRootThemeRightX(), this.pan.getRootThemeMidY(),
						themeLabel.getThemeLeftX(), themeLabel.getThemeMidY());
				this.pan.addConnectLine(this.themeLabel, connectLine);
			} else {
				int distance = 0, distancexMin = 0, distanceyMin = 0, distanceMin = 0;
				ThemeLabel themeTemp = null, themeMin = null;
				boolean isFirst = true;

				for (Entry<ThemeLabel, ConnectLine> item : this.pan.getallConnectLine().entrySet()) {

					themeTemp = item.getKey();
					if (themeLabel != themeTemp) {
						distancex = themeLabel.getThemeLeftX() - themeTemp.getThemeRightX();
						distancey = Math.abs(themeTemp.getThemeMidY() - themeLabel.getThemeMidY());
						distance = (int) Math.sqrt(distancex * distancex + distancey * distancey);
						if (isFirst) {
							isFirst = false;
							distancexMin = distancex;
							distanceyMin = distancey;
							distanceMin = distance;
							themeMin = themeTemp;

							System.out.println("distanceMinx=" + distancexMin);
							System.out.println("distancex=" + distancex);
						} else if (distancex > 0 && distance < distanceMin) {
							distancexMin = distancex;
							distanceyMin = distancey;
							distanceMin = distance;
							themeMin = themeTemp;

							System.out.println("distanceMin=" + distanceMin);
							System.out.println("distancex=" + distancex);

						}
					}

				}

				if (distancexMin > 0 && distancexMin < Constent.minDistancex && distanceyMin < Constent.minDistancey) {
					if (!themeLabel.isChild(themeMin)) {

						if (themeMin.getThemeLeftX() > this.pan.getRootThemeRightX()) {
							themeMin.addChild(themeLabel);
							this.themeLabel.setFather(themeMin);
							ConnectLine connectLine = new ConnectLine(themeMin.getThemeRightX(),
									themeMin.getThemeMidY(), themeLabel.getThemeLeftX(), themeLabel.getThemeMidY());
							this.pan.addConnectLine(this.themeLabel, connectLine);
						} else {
							this.pan.addConnectLine(this.themeLabel, null);
						}
					}

				} else {
					this.pan.addConnectLine(this.themeLabel, null);
				}
			}
		} else {
			if ((distancex < Constent.minDistancex) && (distancey < Constent.minDistancey)) {
				this.pan.rootThemeaddChild(themeLabel);
				this.themeLabel.setFather(this.pan.getrootThemeLabel());
				ConnectLine connectLine = new ConnectLine(this.pan.getRootThemeLeftX(), this.pan.getRootThemeMidY(),
						themeLabel.getThemeRightX(), themeLabel.getThemeMidY());
				this.pan.addConnectLine(this.themeLabel, connectLine);

			} else {
				int distance = 0, distancexMin = 0, distanceyMin = 0, distanceMin = 0;
				ThemeLabel themeTemp = null, themeMin = null;
				boolean isFirst = true;
				for (Entry<ThemeLabel, ConnectLine> item : this.pan.getallConnectLine().entrySet()) {
					themeTemp = item.getKey();
					if (themeLabel != themeTemp) {
						distancex = themeTemp.getThemeLeftX() - themeLabel.getThemeRightX();
						distancey = Math.abs(themeTemp.getThemeMidY() - themeLabel.getThemeMidY());
						distance = (int) Math.sqrt(distancex * distancex + distancey * distancey);
						if (isFirst) {
							isFirst = false;
							distancexMin = distancex;
							distanceyMin = distancey;
							distanceMin = distance;
							themeMin = themeTemp;
						} else if (distancex > 0 && distance < distanceMin) {
							distancexMin = distancex;
							distanceyMin = distancey;
							distanceMin = distance;
							themeMin = themeTemp;

						}
					}
				}
				if (distancexMin > 0 && distancexMin < Constent.minDistancex && distanceyMin < Constent.minDistancey) {
					if (!themeLabel.isChild(themeMin)) {
						if (themeMin.getThemeRightX() < this.pan.getRootThemeLeftX()) {
							themeMin.addChild(themeLabel);
							this.themeLabel.setFather(themeMin);
							ConnectLine connectLine = new ConnectLine(themeMin.getThemeLeftX(), themeMin.getThemeMidY(),
									themeLabel.getThemeRightX(), themeLabel.getThemeMidY());

							this.pan.addConnectLine(this.themeLabel, connectLine);

						} else {
							this.pan.addConnectLine(this.themeLabel, null);
						}
					}
				} else {
					this.pan.addConnectLine(this.themeLabel, null);
				}
			}

		}
	}

	/**
	 * 删除主题
	 * @param themeLabel:右键菜单选中要删除的主题
	 */
	private void deleteHelper(ThemeLabel themeLabel) {
		if (themeLabel != null) {
			if (themeLabel.getFather() != null) {
				/****从父主题列表中删除当前主题******/
				themeLabel.getFather().removeChild(themeLabel);
			}
			ThemeLabel temp = null;
			/****删除孩子****/
			while (themeLabel.getallChild().size() > 0) {
				temp = themeLabel.getallChild().get(0);
				deleteHelper(temp);
			}
			/***设置当前主题状态为"死"****/
			themeLabel.isLive = false;
		} else {
			return;
		}

	}

	/********** 更新主题以及连接线的位置 **********/
	/**
	 * 更新主题和连接线位置
	 * @param themeLabel:被拖动主题
	 * @param deltax:x方向变化量
	 * @param deltay:y方向变化量
	 */
	private void updateConnectHelper(ThemeLabel themeLabel, int deltax, int deltay, boolean isRoot) {

		if (themeLabel != null) {
			/*****更新孩子主题*******/
			for (int i = 0; i < themeLabel.getallChild().size(); i++) {
				ThemeLabel temp = themeLabel.getallChild().get(i);
				temp.setRank(temp.getRank());
				updateConnectHelper(temp, deltax, deltay, false);

			}
			/***** 平移曲线 ********/
			for (int i = 0; i < themeLabel.getCurveLineList().size(); i++) {
				CurveLine curveLine = themeLabel.getCurveLineList().get(i);
				if (curveLine.isLive) {

					Point start = curveLine.getStartPoint();
					Point end = curveLine.getEndPoint();

					if ((start.x == (themeLabel.getX() + themeLabel.getSizeX()) || start.x == (themeLabel.getX()))
							&& start.y == themeLabel.getThemeMidY()) {
						curveLine.updateFirstTwoNode(deltax, deltay);
					} else if ((end.x == (themeLabel.getX() + themeLabel.getSizeX()) || end.x == themeLabel.getX())
							&& end.y == themeLabel.getThemeMidY()) {
						curveLine.updateLastTwoNode(deltax, deltay);
					}

				}
			}
			/****更新当前主题,连接线位置**********/
			themeLabel.updateLocation(deltax + themeLabel.getX(), deltay + themeLabel.getY());

			if (!isRoot) {

				ConnectLine connectLine = this.pan.getConnectLine(themeLabel);
				if (connectLine != null) {
					connectLine.setLocation(deltax + connectLine.getStartX(), deltay + connectLine.getStartY(),
							deltax + connectLine.getEndX(), deltay + connectLine.getEndY());
				}
			} else {
				if (themeLabel.getFather() != null) {
					themeLabel.getFather().removeChild(themeLabel);
					themeLabel.setFather(null);
				}
				if (this.pan.getrootThemeLabel() != themeLabel) {
					this.pan.addConnectLine(themeLabel, null);
				}
			}

		} else {
			return;
		}
	}


	/**
	 * 主题从左到右或从右到左需要镜像
	 * @param themeLabel:被拖动主题
	 * @param x0:对称中心
	 * @param isRoot:是否为拖动主题
	 */
	private void themeLabelMirror(ThemeLabel themeLabel, int x0, boolean isRoot) {
		if (themeLabel != null) {
			/****镜像孩子主题****/
			for (int i = 0; i < themeLabel.getallChild().size(); i++) {
				ThemeLabel temp = themeLabel.getallChild().get(i);
				themeLabelMirror(temp, x0, false);
			}

			/***** 镜像曲线 ********/
			for (int i = 0; i < themeLabel.getCurveLineList().size(); i++) {
				CurveLine curveLine = themeLabel.getCurveLineList().get(i);
				if (curveLine.isLive) {

					Point start = curveLine.getStartPoint();
					Point end = curveLine.getEndPoint();

					if ((start.x == (themeLabel.getX() + themeLabel.getSizeX()) || start.x == (themeLabel.getX()))
							&& start.y == themeLabel.getThemeMidY()) {
						curveLine.mirrorFirstTwoNode(x0);

					} else if ((end.x == (themeLabel.getX() + themeLabel.getSizeX()) || end.x == themeLabel.getX())
							&& end.y == themeLabel.getThemeMidY()) {

						curveLine.mirrorLastTwoNode(x0);
					}

				}
			}
			if (!isRoot) {
				
				/****镜像当前主题和连接线*******/
				themeLabel.updateLocation(2 * x0 - themeLabel.getX() - themeLabel.getSizeX(), themeLabel.getY());
				themeLabel.setVisible(true);

				ConnectLine connectLine = this.pan.getConnectLine(themeLabel);
				if (connectLine != null) {
					connectLine.setLocation(2 * x0 - connectLine.getStartX(), connectLine.getStartY(),
							2 * x0 - connectLine.getEndX(), connectLine.getEndY());
				}
			}

		} else {
			return;
		}
	}

}
