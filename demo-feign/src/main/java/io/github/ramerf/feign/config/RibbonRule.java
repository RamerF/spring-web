package io.github.ramerf.feign.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import java.util.List;

/**
 * 自定义ribbon负载均衡规则,当前毫秒数与服务实例数取余.
 *
 * @author ramer
 * @since 14/06/2020
 */
public class RibbonRule extends AbstractLoadBalancerRule {
  @Override
  public void initWithNiwsConfig(final IClientConfig config) {}

  @Override
  public Server choose(final Object key) {
    return choose(getLoadBalancer(), key);
  }

  @SuppressWarnings({"ConstantConditions", "unused"})
  public Server choose(ILoadBalancer lb, Object key) {
    if (lb == null) {
      return null;
    }
    Server server = null;
    while (server == null) {
      if (Thread.interrupted()) {
        return null;
      }
      List<Server> upList = lb.getReachableServers();
      List<Server> allList = lb.getAllServers();

      int serverCount = allList.size();
      if (serverCount == 0) {
        /*
         * No servers. End regardless of pass, because subsequent passes
         * only get more restrictive.
         */
        return null;
      }

      int index = (int) (System.currentTimeMillis() % serverCount);
      server = upList.get(index);

      if (server == null) {
        /*
         * The only time this should happen is if the server list were
         * somehow trimmed. This is a transient condition. Retry after
         * yielding.
         */
        Thread.yield();
        continue;
      }

      if (server.isAlive() && server.isReadyToServe()) {
        return (server);
      }

      // Shouldn't actually happen.. but must be transient or a bug.
      server = null;
      Thread.yield();
    }

    return server;
  }
}
