package org.jenkinsci.plugins.buildcommentbadge;

import hudson.Extension;
import hudson.model.TaskListener;
import hudson.model.AbstractBuild;
import hudson.model.listeners.RunListener;
import jenkins.model.Jenkins;

/**
* Listener to all build to add the comment action.
*
* @author Julien Henry
*/
@Extension
public class RunListenerImpl extends RunListener<AbstractBuild> {
  public RunListenerImpl() {
    super(AbstractBuild.class);
  }

  @Override
  public void onCompleted(AbstractBuild r, TaskListener listener) {
    BuildCommentBadgePlugin plugin = Jenkins.getInstance().getPlugin(BuildCommentBadgePlugin.class);
    if (plugin.isActivated()) {
      r.addAction(new BuildCommentBadgeAction(r));
    }
    super.onCompleted(r, listener);
  }
}
