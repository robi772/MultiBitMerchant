package org.multibit.mbm.api.response.hal.cart;

import com.google.common.base.Optional;
import com.theoryinpractise.halbuilder.ResourceFactory;
import com.theoryinpractise.halbuilder.spi.Resource;
import org.multibit.mbm.api.response.hal.BaseBridge;
import org.multibit.mbm.db.dto.Cart;
import org.multibit.mbm.db.dto.User;

import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * <p>Bridge to provide the following to {@link org.multibit.mbm.db.dto.Cart}:</p>
 * <ul>
 * <li>Creates representation of multiple Carts for an administrator</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class AdminCartCollectionBridge extends BaseBridge<List<Cart>> {

  private final CustomerCartBridge customerCartBridge;

  /**
   * @param uriInfo   The {@link javax.ws.rs.core.UriInfo} containing the originating request information
   * @param principal An optional {@link org.multibit.mbm.db.dto.User} to provide a security principal
   */
  public AdminCartCollectionBridge(UriInfo uriInfo, Optional<User> principal) {
    super(uriInfo, principal);
    customerCartBridge = new CustomerCartBridge(uriInfo, principal);
  }

  public Resource toResource(List<Cart> carts) {
    ResourceFactory resourceFactory = getResourceFactory();

    Resource cartList = resourceFactory.newResource(this.uriInfo.getRequestUri().toString());

    for (Cart cart : carts) {
      Resource cartResource = customerCartBridge.toResource(cart);

      cartResource.withProperty("id", cart.getId())
      // End of build
      ;

      cartList.withSubresource("/cart/" + cart.getId(), cartResource);
    }

    return cartList;

  }

}
