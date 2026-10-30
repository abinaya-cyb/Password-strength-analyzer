package com.passwordstrengthanalyzer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public final class PasswordStrengthAnalyzerApp {
    private static final Color BACKGROUND = new Color(13, 18, 30);
    private static final Color SURFACE = new Color(23, 31, 47);
    private static final Color INPUT = new Color(16, 23, 37);
    private static final Color TEXT = new Color(235, 241, 250);
    private static final Color MUTED = new Color(151, 165, 187);
    private static final Color ACCENT = new Color(56, 211, 159);
    private static final Color WEAK = new Color(255, 99, 112);
    private static final Color MEDIUM = new Color(255, 190, 85);
    private static final Color STRONG = new Color(56, 211, 159);

    private final JFrame frame = new JFrame("Password Strength Analyzer");
    private final JPasswordField passwordField = new JPasswordField();
    private final char passwordEchoChar = passwordField.getEchoChar();
    private final JToggleButton visibilityButton = new JToggleButton("Show");
    private final JProgressBar strengthBar = new JProgressBar(0, 100);
    private final JLabel strengthLabel = new JLabel("Not checked", SwingConstants.RIGHT);
    private final JTextArea suggestionsArea = new JTextArea(
            "Enter a password, then select Check Strength to see your results.");

    private PasswordStrengthAnalyzerApp() {
        configureFrame();
        buildInterface();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PasswordStrengthAnalyzerApp().frame.setVisible(true));
    }

    private void configureFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(560, 600));
        frame.setSize(760, 760);
        frame.setLocationRelativeTo(null);
    }

    private void buildInterface() {
        JPanel root = new JPanel(new BorderLayout(0, 20));
        root.setBackground(BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(28, 32, 28, 32));

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createScrollableContent(), BorderLayout.CENTER);
        root.add(createFooter(), BorderLayout.SOUTH);
        frame.setContentPane(root);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel eyebrow = new JLabel("SECURITY TOOLKIT");
        eyebrow.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        eyebrow.setForeground(ACCENT);

        JLabel title = new JLabel("Password Strength Analyzer");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 27));
        title.setForeground(TEXT);
        title.setBorder(BorderFactory.createEmptyBorder(7, 0, 5, 0));

        JLabel subtitle = new JLabel("Check the strength of a password locally on your device.");
        subtitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        subtitle.setForeground(MUTED);

        header.add(eyebrow);
        header.add(title);
        header.add(subtitle);
        return header;
    }

    private JScrollPane createScrollableContent() {
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(createPasswordCard());
        content.add(Box.createVerticalStrut(16));
        content.add(createResultsCard());

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    private JPanel createPasswordCard() {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 14));

        JLabel label = new JLabel("Your password");
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        label.setForeground(TEXT);

        passwordField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        passwordField.setForeground(TEXT);
        passwordField.setCaretColor(ACCENT);
        passwordField.setBackground(INPUT);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(54, 68, 91)),
                BorderFactory.createEmptyBorder(11, 12, 11, 12)));
        passwordField.addActionListener(event -> analyzePassword());

        visibilityButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        visibilityButton.setForeground(TEXT);
        visibilityButton.setBackground(INPUT);
        visibilityButton.setFocusPainted(false);
        visibilityButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(54, 68, 91)),
                BorderFactory.createEmptyBorder(0, 16, 0, 16)));
        visibilityButton.addActionListener(event -> togglePasswordVisibility());

        JPanel fieldRow = new JPanel(new BorderLayout(8, 0));
        fieldRow.setOpaque(false);
        fieldRow.add(passwordField, BorderLayout.CENTER);
        fieldRow.add(visibilityButton, BorderLayout.EAST);

        JButton checkButton = createButton("Check Strength", ACCENT, BACKGROUND);
        checkButton.addActionListener(event -> analyzePassword());
        JButton clearButton = createButton("Clear", new Color(42, 53, 72), TEXT);
        clearButton.addActionListener(event -> clearForm());

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(checkButton);
        buttonRow.add(clearButton);

        JPanel inputArea = new JPanel(new GridLayout(0, 1, 0, 10));
        inputArea.setOpaque(false);
        inputArea.add(label);
        inputArea.add(fieldRow);
        inputArea.add(buttonRow);

        card.add(inputArea, BorderLayout.CENTER);
        return card;
    }

    private JPanel createResultsCard() {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 14));

        JLabel heading = new JLabel("Strength overview");
        heading.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        heading.setForeground(TEXT);

        strengthLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        strengthLabel.setForeground(MUTED);

        strengthBar.setValue(0);
        strengthBar.setString("0 / 100");
        strengthBar.setStringPainted(true);
        strengthBar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        strengthBar.setForeground(ACCENT);
        strengthBar.setBackground(INPUT);
        strengthBar.setBorderPainted(false);
        strengthBar.setPreferredSize(new Dimension(100, 22));

        JPanel resultHeader = new JPanel(new BorderLayout(0, 12));
        resultHeader.setOpaque(false);
        resultHeader.add(heading, BorderLayout.NORTH);
        resultHeader.add(strengthLabel, BorderLayout.CENTER);
        resultHeader.add(strengthBar, BorderLayout.SOUTH);

        JLabel suggestionsHeading = new JLabel("Suggestions");
        suggestionsHeading.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        suggestionsHeading.setForeground(TEXT);

        suggestionsArea.setEditable(false);
        suggestionsArea.setFocusable(false);
        suggestionsArea.setLineWrap(true);
        suggestionsArea.setWrapStyleWord(true);
        suggestionsArea.setRows(5);
        suggestionsArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        suggestionsArea.setForeground(MUTED);
        suggestionsArea.setBackground(INPUT);
        suggestionsArea.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        JPanel suggestionPanel = new JPanel(new BorderLayout(0, 9));
        suggestionPanel.setOpaque(false);
        suggestionPanel.add(suggestionsHeading, BorderLayout.NORTH);
        suggestionPanel.add(suggestionsArea, BorderLayout.CENTER);

        card.add(resultHeader, BorderLayout.NORTH);
        card.add(suggestionPanel, BorderLayout.CENTER);
        return card;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        JLabel note = new JLabel("Your password is analyzed locally and is never stored.");
        note.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        note.setForeground(MUTED);
        footer.add(note, BorderLayout.WEST);
        return footer;
    }

    private JPanel createCard() {
        JPanel card = new JPanel();
        card.setBackground(SURFACE);
        Border border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(43, 55, 76)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20));
        card.setBorder(border);
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        return card;
    }

    private JButton createButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        button.setForeground(foreground);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(11, 16, 11, 16));
        return button;
    }

    private void analyzePassword() {
        char[] password = passwordField.getPassword();
        if (password.length == 0) {
            strengthBar.setValue(0);
            strengthBar.setString("0 / 100");
            strengthLabel.setText("Enter a password");
            strengthLabel.setForeground(MUTED);
            suggestionsArea.setText("Type a password above, then select Check Strength.");
            return;
        }

        PasswordStrengthAnalyzer.Analysis analysis = PasswordStrengthAnalyzer.analyze(password);
        Arrays.fill(password, '\0');

        strengthBar.setValue(analysis.score());
        strengthBar.setString(analysis.strength().getLabel() + " - " + analysis.score() + " / 100");
        strengthLabel.setText(analysis.strength().getLabel());
        strengthLabel.setForeground(colorFor(analysis.strength()));
        strengthBar.setForeground(colorFor(analysis.strength()));

        StringBuilder suggestions = new StringBuilder();
        for (String suggestion : analysis.suggestions()) {
            if (!suggestions.isEmpty()) {
                suggestions.append('\n');
            }
            suggestions.append("- ").append(suggestion);
        }
        suggestionsArea.setText(suggestions.toString());
        suggestionsArea.setCaretPosition(0);
    }

    private void togglePasswordVisibility() {
        if (visibilityButton.isSelected()) {
            passwordField.setEchoChar((char) 0);
            visibilityButton.setText("Hide");
        } else {
            passwordField.setEchoChar(passwordEchoChar);
            visibilityButton.setText("Show");
        }
        passwordField.requestFocusInWindow();
    }

    private void clearForm() {
        passwordField.setText("");
        visibilityButton.setSelected(false);
        passwordField.setEchoChar(passwordEchoChar);
        visibilityButton.setText("Show");
        strengthBar.setValue(0);
        strengthBar.setString("0 / 100");
        strengthLabel.setText("Not checked");
        strengthLabel.setForeground(MUTED);
        strengthBar.setForeground(ACCENT);
        suggestionsArea.setText(
                "Enter a password, then select Check Strength to see your results.");
        passwordField.requestFocusInWindow();
    }

    private Color colorFor(PasswordStrengthAnalyzer.Strength strength) {
        return switch (strength) {
            case WEAK -> WEAK;
            case MEDIUM -> MEDIUM;
            case STRONG -> STRONG;
        };
    }
}
