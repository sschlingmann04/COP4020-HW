package plc.project;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.math.BigInteger;
import java.math.BigDecimal;

/**
 * The parser takes the sequence of tokens emitted by the lexer and turns that
 * into a structured representation of the program, called the Abstract Syntax
 * Tree (AST).
 *
 * The parser has a similar architecture to the lexer, just with {@link Token}s
 * instead of characters. As before, {@link #peek(Object...)} and {@link
 * #match(Object...)} are helpers to make the implementation easier.
 *
 * This type of parser is called <em>recursive descent</em>. Each rule in our
 * grammar will have its own function, and reference to other rules correspond
 * to calling that functions.
 */
public final class Parser {

    private final TokenStream tokens;

    public Parser(List<Token> tokens) {
        this.tokens = new TokenStream(tokens);
    }

    /**
     * Parses the {@code source} rule.
     */
    public Ast.Source parseSource() throws ParseException {
        //throw new UnsupportedOperationException(); //TODO
        List<Ast.Field> fields = new ArrayList<>();
        List<Ast.Method> methods = new ArrayList<>();

        while (peek(Token.Type.IDENTIFIER) || match("LET")) {
            fields.add(parseField());
        }

        while (peek("DEF")) {
            methods.add(parseMethod());
        }

        return new Ast.Source(fields, methods);
    }

    /**
     * Parses the {@code field} rule. This method should only be called if the
     * next tokens start a field, aka {@code LET}.
     */
    public Ast.Field parseField() throws ParseException {
        //throw new UnsupportedOperationException(); //TODO
        match("LET");

        String name = tokens.get(-1).getLiteral();
        match(Token.Type.IDENTIFIER);

        Optional<Ast.Expr> value = Optional.empty();

        if (match("=")) {
            value = Optional.of(parseExpression());
        }

        match(";");

        return new Ast.Field(name, value);
    }

    /**
     * Parses the {@code method} rule. This method should only be called if the
     * next tokens start a method, aka {@code DEF}.
     */
    public Ast.Method parseMethod() throws ParseException {
        //throw new UnsupportedOperationException(); //TODO
        match("DEF"); // Match the 'DEF' keyword

        String name = tokens.get(-1).getLiteral();
        match(Token.Type.IDENTIFIER); // Match identifier

        match("("); // Match the opening parenthesis

        List<String> parameters = new ArrayList<>();
        if (peek(Token.Type.IDENTIFIER)) {
            do {
                String paramName = tokens.get(-1).getLiteral();
                match(Token.Type.IDENTIFIER); // Match identifier for parameter
                parameters.add(paramName);
            } while (match(","));
        }

        match(")"); // Match the closing parenthesis
        match("DO"); // Match the 'DO' keyword

        List<Ast.Stmt> statements = new ArrayList<>();
        while (peek("LET") || peek("IF") || peek("FOR") || peek("WHILE") || peek("RETURN") || peek(Token.Type.IDENTIFIER)) {
            statements.add(parseStatement());
        }

        match("END"); // Match the 'END' keyword

        return new Ast.Method(name, parameters, statements);
    }

    /**
     * Parses the {@code statement} rule and delegates to the necessary method.
     * If the next tokens do not start a declaration, if, while, or return
     * statement, then it is an expression/assignment statement.
     */
    public Ast.Stmt parseStatement() throws ParseException {
        Ast.Expr expr = parseExpression();
        if (match("="))
            return new Ast.Stmt.Assignment(expr, parseExpression());
        return new Ast.Stmt.Expression(expr);  // TODO: everything is just expressions for part a, expand this for part b
    }

    /**
     * Parses a declaration statement from the {@code statement} rule. This
     * method should only be called if the next tokens start a declaration
     * statement, aka {@code LET}.
     */
    public Ast.Stmt.Declaration parseDeclarationStatement() throws ParseException {
        throw new UnsupportedOperationException(); //TODO
    }

    /**
     * Parses an if statement from the {@code statement} rule. This method
     * should only be called if the next tokens start an if statement, aka
     * {@code IF}.
     */
    public Ast.Stmt.If parseIfStatement() throws ParseException {
        throw new UnsupportedOperationException(); //TODO
    }

    /**
     * Parses a for statement from the {@code statement} rule. This method
     * should only be called if the next tokens start a for statement, aka
     * {@code FOR}.
     */
    public Ast.Stmt.For parseForStatement() throws ParseException {
        throw new UnsupportedOperationException(); //TODO
    }

    /**
     * Parses a while statement from the {@code statement} rule. This method
     * should only be called if the next tokens start a while statement, aka
     * {@code WHILE}.
     */
    public Ast.Stmt.While parseWhileStatement() throws ParseException {
        throw new UnsupportedOperationException(); //TODO
    }

    /**
     * Parses a return statement from the {@code statement} rule. This method
     * should only be called if the next tokens start a return statement, aka
     * {@code RETURN}.
     */
    public Ast.Stmt.Return parseReturnStatement() throws ParseException {
        throw new UnsupportedOperationException(); //TODO
    }

    /**
     * Parses the {@code expression} rule.
     */
    public Ast.Expr parseExpression() throws ParseException {
        return parseLogicalExpression();
    }

    /**
     * Parses the {@code logical-expression} rule.
     */
    public Ast.Expr parseLogicalExpression() throws ParseException {
        Ast.Expr expr = parseEqualityExpression();
        while (match("AND") || match("OR")) {
            String operator = tokens.get(-1).getLiteral();
            Ast.Expr right = parseEqualityExpression();
            expr = new Ast.Expr.Binary(operator, expr, right);
        }
        return expr;
    }

    /**
     * Parses the {@code equality-expression} rule.
     */
    public Ast.Expr parseEqualityExpression() throws ParseException {
        Ast.Expr expr = parseAdditiveExpression();
        while (match("<") || match(">") || match("<=") || match(">=") || match("!=") || match("==")) {
            String operator = tokens.get(-1).getLiteral();
            Ast.Expr right = parseAdditiveExpression();
            expr = new Ast.Expr.Binary(operator, expr, right);
        }
        return expr;
    }

    /**
     * Parses the {@code additive-expression} rule.
     */
    public Ast.Expr parseAdditiveExpression() throws ParseException {
        Ast.Expr expr = parseMultiplicativeExpression();
        while (match("+") || match("-")) {
            String operator = tokens.get(-1).getLiteral();
            Ast.Expr right = parseMultiplicativeExpression();
            expr = new Ast.Expr.Binary(operator, expr, right);
        }
        return expr;
    }

    /**
     * Parses the {@code multiplicative-expression} rule.
     */
    public Ast.Expr parseMultiplicativeExpression() throws ParseException {
        Ast.Expr expr = parseSecondaryExpression();
        while (match("*") || match("/")) {
            String operator = tokens.get(-1).getLiteral();
            Ast.Expr right = parseSecondaryExpression();
            expr = new Ast.Expr.Binary(operator, expr, right);
        }
        return expr;
    }

    /**
     * Parses the {@code secondary-expression} rule.
     */
    public Ast.Expr parseSecondaryExpression() throws ParseException {
        return parsePrimaryExpression();  // TODO: finish secondary expression
    }

    /**
     * Parses the {@code primary-expression} rule. This is the top-level rule
     * for expressions and includes literal values, grouping, variables, and
     * functions. It may be helpful to break these up into other methods but is
     * not strictly necessary.
     */
    public Ast.Expr parsePrimaryExpression() throws ParseException {
        //throw new UnsupportedOperationException(); //TODO
        if (match("TRUE")) {
            return new Ast.Expr.Literal(true);
        } else if (match("FALSE")) {
            return new Ast.Expr.Literal(false);
        } else if (match("NIL")) {
            return new Ast.Expr.Literal(null);
        } else if (match(Token.Type.INTEGER)) {
            return new Ast.Expr.Literal(BigInteger.valueOf(Integer.parseInt(tokens.get(-1).getLiteral())));
        } else if (match(Token.Type.DECIMAL)) {
            return new Ast.Expr.Literal(BigDecimal.valueOf(Double.parseDouble(tokens.get(-1).getLiteral())));
        } else if (match(Token.Type.CHARACTER)) {
            String str = tokens.get(-1).getLiteral().substring(1, tokens.get(-1).getLiteral().length() - 1);
            if (str.charAt(0) == '\\')
                str = str.replace("\\b", "\b").replace("\\n", "\n")
                         .replace("\\r", "\r").replace("\\t", "\t")
                         .replace("\\'", "'").replace("\\\"", "\"")
                         .replace("\\\\", "\\");
            char ch = str.charAt(0);
            return new Ast.Expr.Literal(ch);
        } else if (match(Token.Type.STRING)) {
            String str = tokens.get(-1).getLiteral().substring(1, tokens.get(-1).getLiteral().length() - 1);
            str = str.replace("\\b", "\b").replace("\\n", "\n")
                     .replace("\\r", "\r").replace("\\t", "\t")
                     .replace("\\'", "'").replace("\\", "\"")
                     .replace("\\\\", "\\");
            return new Ast.Expr.Literal(str);
        } else if (match("(")) {
            Ast.Expr expression = parseExpression(); // Assuming you have a method for parsing expressions
            if (!match(")")) {
                throw new ParseException("Expected closing parenthesis.", tokens.get(-1).getIndex());
            }
            return new Ast.Expr.Group(expression);
        } else if (match(Token.Type.IDENTIFIER)) {
            String name = tokens.get(-1).getLiteral();
            if (match("(")) { // Check if this is a function call
                List<Ast.Expr> arguments = new ArrayList<>();
                if (!peek(")")) { // If the next token is not a closing parenthesis
                    do {
                        arguments.add(parseExpression()); // Assuming you have a method for parsing expressions
                    } while (match(","));
                }
                if (!match(")")) {
                    throw new ParseException("Expected closing parenthesis for function call.", tokens.get(-1).getIndex());
                }
                return new Ast.Expr.Function(Optional.empty(), name, arguments);
            }
            return new Ast.Expr.Access(Optional.empty(), name); // Regular identifier access
        } else {
            throw new ParseException("Invalid primary expression.", tokens.get(-1).getIndex());
        }
    }

    /**
     * As in the lexer, returns {@code true} if the current sequence of tokens
     * matches the given patterns. Unlike the lexer, the pattern is not a regex;
     * instead it is either a {@link Token.Type}, which matches if the token's
     * type is the same, or a {@link String}, which matches if the token's
     * literal is the same.
     *
     * In other words, {@code Token(IDENTIFIER, "literal")} is matched by both
     * {@code peek(Token.Type.IDENTIFIER)} and {@code peek("literal")}.
     */
    private boolean peek(Object... patterns) {
        for (int i = 0; i < patterns.length; i++) {
            if (!tokens.has(i)) {
                return false;
            } else if (patterns[i] instanceof Token.Type) {
                if (patterns[i] != tokens.get(i).getType()) {
                    return false;
                }
            } else if (patterns[i] instanceof String) {
                if (!patterns[i].equals(tokens.get(i).getLiteral())) {
                    return false;
                }
            } else {
                throw new AssertionError("Invalid pattern object: " +
                        patterns[i].getClass());
            }
        }
        return true;
    }

    /**
     * As in the lexer, returns {@code true} if {@link #peek(Object...)} is true
     * and advances the token stream.
     */
    private boolean match(Object... patterns) {
        boolean peek = peek(patterns);
        if (peek) {
            for (int i = 0; i < patterns.length; i++) {
                tokens.advance();
            }
        }
        return peek;
    }


    private static final class TokenStream {

        private final List<Token> tokens;
        private int index = 0;

        private TokenStream(List<Token> tokens) {
            this.tokens = tokens;
        }

        /**
         * Returns true if there is a token at index + offset.
         */
        public boolean has(int offset) {
            return index + offset < tokens.size();
        }

        /**
         * Gets the token at index + offset.
         */
        public Token get(int offset) {
            return tokens.get(index + offset);
        }

        /**
         * Advances to the next token, incrementing the index.
         */
        public void advance() {
            index++;
        }

    }

}