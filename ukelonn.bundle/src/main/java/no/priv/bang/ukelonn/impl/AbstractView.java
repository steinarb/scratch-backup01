/*
 * Copyright 2016-2017 Steinar Bang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */
package no.priv.bang.ukelonn.impl;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.navigator.View;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public abstract class AbstractView extends VerticalLayout implements View {
    private static final long serialVersionUID = 267153275586375959L;
    public static URI addPathToURI(URI location, String path) {
        String combinedPath = location.getPath() + path;
        URI newURI = location.resolve(combinedPath);
        return newURI;
    }

    protected Table createTransactionTable(String transactionTypeName, BeanItemContainer<Transaction> transactions, boolean addPaidOutColumn) {
        Table transactionsTable = new Table();
        transactionsTable.addContainerProperty("transactionTime", Date.class, null, "Dato", null, null);
        transactionsTable.addContainerProperty("name", String.class, null, transactionTypeName, null, null);
        transactionsTable.addContainerProperty("transactionAmount", Double.class, null, "Beløp", null, null);
        ArrayList<String> visibleColumns = new ArrayList<String>(Arrays.asList("transactionTime", "name", "transactionAmount"));
        if (addPaidOutColumn) {
            transactionsTable.addContainerProperty("paidOut", CheckBox.class, null, "Utbetalt", null, null);
            visibleColumns.add("paidOut");
        }

        transactionsTable.setConverter("transactionTime", dateFormatter);
        transactionsTable.setContainerDataSource(transactions);
        transactionsTable.setVisibleColumns(visibleColumns.toArray(new Object[visibleColumns.size()]));
        transactionsTable.setPageLength(CommonDatabaseMethods.NUMBER_OF_TRANSACTIONS_TO_DISPLAY);
        return transactionsTable;
    }

    protected NavigationView createNavigationViewWithTable(NavigationManager navigationManager, String tableTitle, BeanItemContainer<Transaction> transactions, String navigationViewCaption, boolean addPaidOutColumn) {
        CssLayout transactionTableForm = new CssLayout();
        VerticalComponentGroup transactionTableGroup = new VerticalComponentGroup();
        Table transactionTable = createTransactionTable(tableTitle, transactions, addPaidOutColumn);
        transactionTableGroup.addComponent(transactionTable);
        transactionTableForm.addComponent(transactionTableGroup);
        NavigationView transactionTableView = new NavigationView(navigationViewCaption, transactionTableForm);
        navigationManager.addComponent(transactionTableView);
        navigationManager.navigateTo(transactionTableView);
        navigationManager.navigateBack();
        return transactionTableView;
    }

    protected NavigationButton createNavigationButton(String caption, NavigationView targetView) {
        NavigationButton button = new NavigationButton(caption, targetView);
        return button;
    }

    protected HorizontalLayout createLinksToBrowserVersionAndLogout(VaadinRequest request, String uiStyle, String uiStyleName) {
        HorizontalLayout links = new HorizontalLayout();
        links.setSpacing(true);
        links.setWidth("100%");
        Link linkToTop = new Link("Tilbake til topp", new ExternalResource(request.getContextPath() + "/.."));
        links.addComponent(linkToTop);
        Link linkToBrowserFriendlyUI = new Link(uiStyleName, new ExternalResource(request.getContextPath() + "?ui-style=" + uiStyle));
        links.addComponent(linkToBrowserFriendlyUI);
        Link linkToLogout = new Link("Logg ut", new ExternalResource(request.getContextPath() + "?logout=yes"));
        links.addComponent(linkToLogout);
        links.setComponentAlignment(linkToLogout, Alignment.MIDDLE_RIGHT);
        return links;
    }

    protected Component wrapInPanel(Component wrappedComponent) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
        Label indent = new Label(" ");
        indent.setWidth("1mm");
        horizontalLayout.addComponent(indent);
        Panel panel = new Panel(wrappedComponent);
        panel.setWidth("100%");
        horizontalLayout.addComponent(panel);
        horizontalLayout.setExpandRatio(panel, 1);
        return horizontalLayout;
    }

    static final StringToDateConverter dateFormatter =
        new StringToDateConverter() {
            private static final long serialVersionUID = -1728291825811483452L;

            @Override
            public DateFormat getFormat(Locale locale) {
                return new SimpleDateFormat("yyyy-MM-dd");
            }
        };

}
