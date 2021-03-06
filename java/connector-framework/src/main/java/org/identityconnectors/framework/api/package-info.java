/*
 * ====================
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 ConnId. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License("CDDL") (the "License").  You may not use this file
 * except in compliance with the License.
 *
 * You can obtain a copy of the License at
 * http://opensource.org/licenses/cddl1.php
 * See the License for the specific language governing permissions and limitations
 * under the License.
 *
 * When distributing the Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://opensource.org/licenses/cddl1.php.
 * If applicable, add the following below this CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * ====================
 */

/**
 * The Connector API presents a consistent view of any Connector,
 *         regardless of which operations the connector actually implements.
 *         Common code implements the API in terms of the SPI,
 *         providing consistent behavior and common features.
 *         The developer of each Connector bundle implements (some subset of) the SPI,
 *         and thus inherits a certain amount of common behavior "for free".
 *
 * <p> For example, the common code that implements the API:
 * <ul>
 * <li>Provides <em>connection pooling</em> to each Connector that requires it.
 *         Not every Connector needs connection pooling,
 *         but those that do can "opt-in" simply by implementing
 *         {@link org.identityconnectors.framework.spi.PoolableConnector a specific interface}.
 *         The calling application that uses the API does not need to do anything
 *         in order to enable connection pooling, but can
 *         {@link org.identityconnectors.framework.api.APIConfiguration#getConnectorPoolConfiguration configure connection pooling}.
 * </li>
 *
 * <li>Provides <em>timeouts</em> for all Connector operations.
 *         Common code provides this transparently to each implementation of the SPI.
 *         The API consumer may configure an appropriate timeout if the default is unacceptable.
 * </li>
 *
 * <li>Provides <em>default implementations of advanced operations</em>.
 *         A Connector developer may implement either simple or advanced versions
 *         of certain {@link org.identityconnectors.framework.spi.operations SPI operations}
 *         such as update or search.
 *         The API consumer sees only the advanced version of such operations,
 *         regardless of which version the Connector developer implements.
 * </li>
 * </ul>
 *
 * <p>In order to use the Connector API, an application must first
 *         use the {@link org.identityconnectors.framework.api.ConnectorInfoManagerFactory}
 *         to load a set of connector bundles.  Connector bundles can be loaded
 *         {@link org.identityconnectors.framework.api.ConnectorInfoManagerFactory#getLocalManager locally}
 *         or {@link org.identityconnectors.framework.api.ConnectorInfoManagerFactory#getRemoteManager remotely}.
 *         In either case, the {@link org.identityconnectors.framework.api.ConnectorInfoManager}
 *         that is returned allows the application to obtain an instance of
 *         {@link org.identityconnectors.framework.api.ConnectorInfo}
 *         that describes each of the available connector bundles.
 * </p>
 * <p>The application then uses the <code>ConnectorInfo</code> to configure an instance of the connector.
 *         The application {@link org.identityconnectors.framework.api.ConnectorInfo#createDefaultAPIConfiguration obtains the default configuration},
 *         {@link org.identityconnectors.framework.api.APIConfiguration#getConfigurationProperties lists the available configuration properties},
 *         and then {@link org.identityconnectors.framework.api.ConfigurationProperty#setValue sets values for configuration properties}.
 *         Connector configuration properties typically include such target-specific information
 *         such as hostname, port number and the username and password to use in connecting to the target.
 *         The application then passes the <code>APIConfiguration</code> that it has tailored
 *         into {@link org.identityconnectors.framework.api.ConnectorFacadeFactory#newInstance}
 *         to obtain an instance of {@link org.identityconnectors.framework.api.ConnectorFacade}.
 *         An instance of <code>ConnectorFacade</code> represents a configured instance of a connector.
 * </p>
 * <p>Once the application has an instance of <code>ConnectorFacade</code>, the application can invoke
 *         {@link org.identityconnectors.framework.api.ConnectorFacade#getSupportedOperations any operation that it supports}.
 *         In some cases, a connector facade may support certain operations only for certain object-classes.
 *         Each instance of <code>ConnectorFacade</code>
 *         {@link org.identityconnectors.framework.api.operations.SchemaApiOp#schema describes the object-classes and the operations that it supports}.
 */
package org.identityconnectors.framework.api;
