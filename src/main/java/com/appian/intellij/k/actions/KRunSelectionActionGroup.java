package com.appian.intellij.k.actions;

import static com.appian.intellij.k.KIcons.RUN_SELECTION;
import static com.appian.intellij.k.actions.KActionUtil.getEditorSelection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.appian.intellij.k.settings.KSettingsService;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Separator;
import com.intellij.openapi.components.ServiceManager;

/**
 * An action group consisting of an action to run selected
 * code on the new server or any of the servers, defined
 * in settings.
 */
public class KRunSelectionActionGroup extends ActionGroup {
  @NotNull
  @Override
  public AnAction[] getChildren(@Nullable AnActionEvent e) {
    if (e == null) {
      return new AnAction[0];
    }

    List<KRunSelectionAction> serverActions = ServiceManager.getService(KSettingsService.class)
        .getSettings()
        .getServers()
        .stream()
        .map(s -> new KRunSelectionAction(e.getProject(), s.toString(), s.getId()))
        .collect(Collectors.toList());

    List<AnAction> actions = new ArrayList<>();

    if (!serverActions.isEmpty()) {
      actions.addAll(serverActions);
      actions.add(Separator.getInstance());
    }

    actions.add(new KRunSelectionAction(e.getProject(), "New Server...", null));
    return actions.toArray(new AnAction[0]);
  }

  @Override
  public void update(AnActionEvent event) {
    event.getPresentation().setIcon(RUN_SELECTION);

    Optional<String> selection = getEditorSelection(event);
    event.getPresentation().setEnabled(selection.isPresent());
    event.getPresentation().setVisible(selection.isPresent());
  }
}
