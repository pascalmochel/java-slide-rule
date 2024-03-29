package org.homs.slide;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Rectangle;

import org.homs.slide.awt.WL;

/**
 * <h3>Pickett N909</h3>
 * 
 * <pre>
 * K A [B T S CI C] D L
 * </pre>
 * 
 * <h3>Pickett 160-ES Microline</h3>
 * 
 * <pre>
 * K A [B CI C] D L
 * </pre>
 * 
 * <h3>Martilog Slide Rule</h3>
 * 
 * <pre>
 * K A [B S T CI CF C] D DF L R1 R2 LL0 LL1 LL2 LL3
 * </pre>
 * 
 * <pre>
 * http://nsg.upor.net/slide/yasre/rule.htm?%0A%22T1%22T,T2~,K_,A,DF[CF,B,CIF,CI,C]D,DI,S,ST,P/LL03,LL02,LL01,LL00,%22W2%22logr22[%22W2'%22logr22,CI,%22L%22lin5,C,%22W1'%22logr21]%22W1%22logr21,LL0_,LL1~,LL2_,LL3~,'FC%2062/83%0A'TITLE
 * </pre>
 * 
 * <br>
 * <br>
 * K - x^3<br>
 * A, B - x<br>
 * S - arcsin(x)<br>
 * T - arctan(x)<br>
 * CI - 1/x <br>
 * CF,DF - pi-folded x<br>
 * C, D - x <br>
 * L - log10(x)<br>
 * R1, R2 - sqrt<br>
 * <br>
 * 
 * @author mhoms
 */
public class SlideRuleMain {

	public static void main(final String[] args) {

		new SlideRuleMain().frameFactory();
	}

	protected Frame frameFactory() {
		final Frame f = new Frame("K A [B S T ST CT CI CF C] D DF L R1 R2 LL0 LL1 LL2 LL3");
		f.setBounds(new Rectangle(100, 100, 100 + 1000, 100 + 255 + 60 + 100 + 40 + 25 + 30));

		f.addWindowListener(new WL());

		f.setLayout(new BorderLayout());

		final CPanel slideRulePanel = new CPanel();
		final ControlPanel controlPanel = new ControlPanel(slideRulePanel);
		f.add(controlPanel, "North");
		f.add(slideRulePanel, "Center");
		f.setResizable(false);

		f.setVisible(true);
		f.repaint();

		return f;
	}

}