package org.multibit.mbm.api.response.hal;

import com.google.common.base.Optional;
import com.theoryinpractise.halbuilder.ResourceFactory;
import com.theoryinpractise.halbuilder.spi.Resource;
import org.multibit.mbm.api.response.CartItemResponse;
import org.multibit.mbm.api.response.CartResponse;
import org.multibit.mbm.db.dto.User;

import javax.ws.rs.core.UriInfo;

/**
 * <p>Bridge to provide the following to {@link org.multibit.mbm.db.dto.Customer}:</p>
 * <ul>
 * <li>Creates {@link com.theoryinpractise.halbuilder.spi.Resource} representations</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class DefaultCartResponseBridge extends BaseBridge<CartResponse> {

  /**
   * @param uriInfo   The {@link javax.ws.rs.core.UriInfo} containing the originating request information
   * @param principal An optional {@link org.multibit.mbm.db.dto.User} to provide a security principal
   */
  public DefaultCartResponseBridge(UriInfo uriInfo, Optional<User> principal) {
    super(uriInfo, principal);
  }

  public Resource toResource(CartResponse cartResponse) {

    String basePath = "/cart/" + cartResponse.getId();

    Resource cartResource = getResourceFactory().newResource(basePath)
      .withProperty("btcTotal", cartResponse.getBtcTotal())
      .withProperty("localSymbol", cartResponse.getLocalSymbol())
      .withProperty("localTotal", cartResponse.getLocalTotal());

    for (CartItemResponse cartItemResponse: cartResponse.getCartItems()) {
      cartResource.withBeanBasedSubresource("item",basePath+"/item/"+cartItemResponse.getId(),cartItemResponse);
    }

    return cartResource;
  }

}
