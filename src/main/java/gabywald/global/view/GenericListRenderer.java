package gabywald.global.view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

/**
 * This class defines a specific List Cell Renderer for Models in a JList. 
 * @author Gabriel Chandesris (2011)
 */
public class GenericListRenderer implements ListCellRenderer {
	public Component getListCellRendererComponent(JList list, Object value,
							int index, boolean isSelected, boolean cellHasFocus) {
		JLabel tmp = new JLabel((String)value);
		tmp.setHorizontalAlignment(SwingConstants.CENTER);
		tmp.setText((String)list.getModel().getElementAt(index));
		tmp.setOpaque(true);
		if (index%2 != 0) {
			tmp.setBackground((!isSelected)?Color.LIGHT_GRAY:Color.DARK_GRAY);
			tmp.setForeground((!isSelected)?Color.BLACK:Color.CYAN);
		} else { 
			tmp.setBackground((!isSelected)?Color.WHITE:Color.DARK_GRAY);
			tmp.setForeground((!isSelected)?Color.BLACK:Color.CYAN);
		}
		return tmp;
	}
	
}
