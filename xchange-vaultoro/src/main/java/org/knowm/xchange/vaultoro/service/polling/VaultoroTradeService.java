package org.knowm.xchange.vaultoro.service.polling;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.polling.trade.PollingTradeService;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.polling.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.vaultoro.VaultoroAdapters;
import org.knowm.xchange.vaultoro.dto.trade.VaultoroCancelOrderResponse;
import org.knowm.xchange.vaultoro.dto.trade.VaultoroNewOrderResponse;

public class VaultoroTradeService extends VaultoroTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public VaultoroTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public boolean cancelOrder(String arg0)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    try {
      VaultoroCancelOrderResponse response = super.cancelVaultoroOrder(arg0);
      if (response.getStatus().equals("success")) {
        return true;
      } else {
        return false;
      }
    } catch (ExchangeException e) {
      return false;
    }
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return VaultoroAdapters.adaptVaultoroOpenOrders(getVaultoroOrders());
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams arg0) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder arg0)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    VaultoroNewOrderResponse response = super.placeLimitOrder(arg0.getCurrencyPair(), arg0.getType(), arg0.getTradableAmount(), arg0.getLimitPrice());
    return response.getData().getOrderID();

  }

  @Override
  public String placeMarketOrder(MarketOrder arg0)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    VaultoroNewOrderResponse response = super.placeMarketOrder(arg0.getCurrencyPair(), arg0.getType(), arg0.getTradableAmount());
    return response.getData().getOrderID();

  }

  @Override
  public Collection<Order> getOrder(String... arg0)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotAvailableFromExchangeException();

  }

}