/*
 * Copyright 2012-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.initializr.generator.language.groovy;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.spring.initializr.generator.language.Annotatable;
import io.spring.initializr.generator.language.Annotation;
import io.spring.initializr.generator.language.CodeBlock;
import io.spring.initializr.generator.language.Parameter;

/**
 * Declaration of a method written in Groovy.
 *
 * @author Stephane Nicoll
 */
public final class GroovyMethodDeclaration implements Annotatable {

	private final List<Annotation> annotations = new ArrayList<>();

	private final String name;

	private final String returnType;

	private final int modifiers;

	private final List<Parameter> parameters;

	private final CodeBlock code;

	private final List<GroovyStatement> statements;

	private GroovyMethodDeclaration(Builder builder, CodeBlock code) {
		this.name = builder.name;
		this.returnType = builder.returnType;
		this.modifiers = builder.modifiers;
		this.parameters = List.copyOf(builder.parameters);
		this.code = code;
		this.statements = Collections.emptyList();
	}

	private GroovyMethodDeclaration(Builder builder, List<GroovyStatement> statements) {
		this.name = builder.name;
		this.returnType = builder.returnType;
		this.modifiers = builder.modifiers;
		this.parameters = List.copyOf(builder.parameters);
		this.code = CodeBlock.of("");
		this.statements = List.copyOf(statements);
	}

	public static Builder method(String name) {
		return new Builder(name);
	}

	String getName() {
		return this.name;
	}

	String getReturnType() {
		return this.returnType;
	}

	List<Parameter> getParameters() {
		return this.parameters;
	}

	int getModifiers() {
		return this.modifiers;
	}

	CodeBlock getCode() {
		return this.code;
	}

	@Deprecated(since = "0.20.0", forRemoval = true)
	public List<GroovyStatement> getStatements() {
		return this.statements;
	}

	@Override
	public void annotate(Annotation annotation) {
		this.annotations.add(annotation);
	}

	@Override
	public List<Annotation> getAnnotations() {
		return Collections.unmodifiableList(this.annotations);
	}

	/**
	 * Builder for creating a {@link GroovyMethodDeclaration}.
	 */
	public static final class Builder {

		private final String name;

		private List<Parameter> parameters = new ArrayList<>();

		private String returnType = "void";

		private int modifiers = Modifier.PUBLIC;

		private Builder(String name) {
			this.name = name;
		}

		public Builder modifiers(int modifiers) {
			this.modifiers = modifiers;
			return this;
		}

		public Builder returning(String returnType) {
			this.returnType = returnType;
			return this;
		}

		public Builder parameters(Parameter... parameters) {
			this.parameters = Arrays.asList(parameters);
			return this;
		}

		public GroovyMethodDeclaration body(CodeBlock code) {
			return new GroovyMethodDeclaration(this, code);
		}

		@Deprecated(since = "0.20.0", forRemoval = true)
		public GroovyMethodDeclaration body(GroovyStatement... statements) {
			return new GroovyMethodDeclaration(this, Arrays.asList(statements));
		}

	}

}
