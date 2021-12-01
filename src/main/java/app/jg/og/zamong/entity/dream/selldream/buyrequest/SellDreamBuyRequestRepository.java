package app.jg.og.zamong.entity.dream.selldream.buyrequest;

import app.jg.og.zamong.entity.dream.selldream.SellDream;
import app.jg.og.zamong.entity.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface SellDreamBuyRequestRepository extends CrudRepository<SellDreamBuyRequest, UUID> {

    SellDreamBuyRequest findByUserAndSellDream(User user, SellDream sellDream);
    List<SellDreamBuyRequest> findBySellDream(SellDream sellDream);
    List<SellDreamBuyRequest> findByUser(User user);
}
