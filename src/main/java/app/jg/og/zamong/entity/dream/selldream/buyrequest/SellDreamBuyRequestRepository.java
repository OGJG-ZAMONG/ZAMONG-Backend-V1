package app.jg.og.zamong.entity.dream.selldream.buyrequest;

import app.jg.og.zamong.entity.dream.selldream.SellDream;
import app.jg.og.zamong.entity.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SellDreamBuyRequestRepository extends CrudRepository<SellDreamBuyRequest, UUID> {

    SellDreamBuyRequest findByUserAndSellDream(User user, SellDream sellDream);
}
