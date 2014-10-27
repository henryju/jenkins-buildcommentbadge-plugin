package org.jenkinsci.plugins.buildcommentbadge;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.model.Job;
import hudson.model.Run;
import hudson.views.ListViewColumnDescriptor;
import hudson.views.ListViewColumn;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;

/**
 * Column showing last build comment
 * 
 * @author Julien Henry
 * 
 */
public class LastBuildCommentColumn extends ListViewColumn {
  private static final class BuildNodeColumnDescriptor extends ListViewColumnDescriptor {
    @Override
    public String getDisplayName() {
      return Messages.LastBuildCommentColumn_DisplayName();
    }

    @Override
    public ListViewColumn newInstance(final StaplerRequest request, final JSONObject formData)
      throws FormException {
      return new LastBuildCommentColumn();
    }

    @Override
    public boolean shownByDefault() {
      return false;
    }
  }

  @Extension
  public static final Descriptor<ListViewColumn> DESCRIPTOR = new BuildNodeColumnDescriptor();

  @Override
  public Descriptor<ListViewColumn> getDescriptor() {
    return DESCRIPTOR;
  }

  public String getLastBuildComment(Job job) {
    Run r = job.getLastBuild();
    if (r != null) {
      BuildCommentBadgeAction a = r.getAction(BuildCommentBadgeAction.class);
      if (a != null) {
        return a.getComment();
      }
    }
    return null;
  }
}
